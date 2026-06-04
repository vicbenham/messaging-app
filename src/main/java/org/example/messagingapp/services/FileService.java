package org.example.messagingapp.services;

import org.example.messagingapp.dtos.SendFile;
import org.example.messagingapp.entities.Chat;
import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.entities.Message;
import org.example.messagingapp.enums.MessageStatus;
import org.example.messagingapp.enums.MessageType;
import org.example.messagingapp.exceptions.ConflictException;
import org.example.messagingapp.exceptions.ForbiddenException;
import org.example.messagingapp.exceptions.NotFoundException;
import org.example.messagingapp.repositories.ChatRepository;
import org.example.messagingapp.repositories.ContactRepository;
import org.example.messagingapp.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FileService {

    @Value("${messagingApp.uploads.location}")
    private String uploadDir;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Transactional
    public void uploadFile(SendFile input, Long userId) {

        Contact me = contactRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Contact not found"));

        Chat chat = chatRepository.findById(input.chatId())
                .orElseThrow(() -> new NotFoundException("Chat not found"));
        if (!chat.getSender().equals(me) && !chat.getReceiver().equals(me)) {
            throw new ForbiddenException("You are not part of this chat");
        }

        if (input.file() == null) {
            throw new ConflictException("No file provided");
        }

        /*
         * Vérification que l'utilisateur appartient au chat.
         * Garde ce bloc uniquement si Chat possède bien sender et receiver.
         */
        if (!chat.getSender().equals(me) && !chat.getReceiver().equals(me)) {
            throw new ForbiddenException("You are not part of this chat");
        }

        MultipartFile file = input.file();

        String fileName =
                UUID.randomUUID().toString()
                        + file.getOriginalFilename();

        String content = store(file, fileName);

        Message message = Message.builder()
                .content(content)
                .type(MessageType.FILE)
                .chatId(chat.getId())
                .sentAt(LocalDateTime.now())
                .isEdited(false)
                .status(MessageStatus.SENT)
                .sender(me)
                .build();

        messageRepository.save(message);

        auditLogService.logMessage(message);
    }

    private String store(MultipartFile file, String fileName) {

        Path uploadedPath = Paths.get(uploadDir);
        Path target = uploadedPath.resolve(fileName);

        try (InputStream in = file.getInputStream()) {

            Files.copy(
                    in,
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return target.toString();
    }
}