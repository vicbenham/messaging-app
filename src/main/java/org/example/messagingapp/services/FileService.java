package org.example.messagingapp.services;

import org.example.messagingapp.dtos.SendFile;
import org.example.messagingapp.entities.Chat;
import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.entities.Message;
import org.example.messagingapp.enums.MessageStatus;
import org.example.messagingapp.enums.MessageType;
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
import java.util.Optional;
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
    public void uploadFile(SendFile input, Long userId){
        Optional<Contact> optionalSender = contactRepository.findById(userId);
        Contact me = optionalSender.orElseThrow(() -> new RuntimeException("Contact not found"));

        Optional<Chat> optionalChat = chatRepository.findById(input.chatId());
        Chat chat = optionalChat.orElseThrow(() -> new RuntimeException("Chat not found"));
        String content = "";
        //upload, create and save message, notify and logs
        if ((input.file() != null)) {
            MultipartFile file = input.file();
            String baseName = UUID.randomUUID().toString();
            String fileName = baseName
                    + file.getOriginalFilename();
            content = store(file, fileName);
        }
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

    private String store(MultipartFile file,
                       String fileName) {
        Path uploadedPath = Paths.get(uploadDir);
        Path target = uploadedPath.resolve(fileName);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return target.toString();
    }

}
