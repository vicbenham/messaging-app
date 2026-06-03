package org.example.messagingapp.services;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.EditMessage;
import org.example.messagingapp.dtos.Notification;
import org.example.messagingapp.dtos.SendMessage;
import org.example.messagingapp.entities.Chat;
import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.entities.Message;
import org.example.messagingapp.enums.ChatStatus;
import org.example.messagingapp.enums.MessageStatus;
import org.example.messagingapp.enums.NotificationType;
import org.example.messagingapp.repositories.ChatRepository;
import org.example.messagingapp.repositories.ContactRepository;
import org.example.messagingapp.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final ContactRepository contactRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final NotificationService notificationService;

    @Transactional
    public void sendMessage(SendMessage request, Long userId) {
        Optional<Contact> optionalSender = contactRepository.findById(userId);
        Contact me = optionalSender.orElseThrow(() -> new RuntimeException("Contact not found"));

        Optional<Chat> optionalChat = chatRepository.findById(request.chatId());
        Chat chat = optionalChat.orElseThrow(() -> new RuntimeException("Chat not found"));

         if (!chat.getSender().getId().equals(me.getId()) && !chat.getReceiver().getId().equals(me.getId())) {
             throw new RuntimeException("User is not part of the chat");
         }

         if(!chat.getStatus().equals(ChatStatus.ACCEPTED)) {
            throw new RuntimeException("Chat status is not ACCEPTED");
        }

        Message message = Message.builder()
                .content(request.content())
                .sender(me)
                .chatId(request.chatId())
                .status(MessageStatus.SENT)
                .sentAt(LocalDateTime.now())
                .build();
        messageRepository.save(message);
        Contact receiver = me.equals(chat.getSender()) ? chat.getReceiver() : chat.getSender();
        notificationService.sendMessageToUser(
                receiver.getUsername(),
                new Notification(
                        receiver.getUsername() + " sent you message",
                        LocalDateTime.now(),
                        NotificationType.NEW_MESSAGE));
    }

    @Transactional
    public void editMessage(EditMessage request, Long userId){
        Optional<Contact> optionalSender = contactRepository.findById(userId);
        Contact me = optionalSender.orElseThrow(() -> new RuntimeException("Contact not found"));

        Optional<Chat> optionalChat = chatRepository.findById(request.chatId());
        Chat chat = optionalChat.orElseThrow(() -> new RuntimeException("Chat not found"));

        if (!chat.getSender().getId().equals(me.getId()) && !chat.getReceiver().getId().equals(me.getId())) {
            throw new RuntimeException("User is not part of the chat");
        }

        if(!chat.getStatus().equals(ChatStatus.ACCEPTED)) {
            throw new RuntimeException("Chat status is not ACCEPTED");
        }

        Optional<Message> optionalMessage = messageRepository.findById(request.messageId());
        Message message = optionalMessage.orElseThrow(()-> new RuntimeException("Message not found"));

        if(!message.getSender().equals(me)){
            throw new RuntimeException("You cannot modify a message you didn't write");
        }
        if(!message.getChatId().equals(chat.getId())){
            throw new RuntimeException("This message is not in this right chat");
        }

        message.setIsEdited(true);
        message.setContent(request.content());

        messageRepository.save(message);

        Contact receiver = me.equals(chat.getSender()) ? chat.getReceiver() : chat.getSender();
        notificationService.sendMessageToUser(
                receiver.getUsername(),
                new Notification(
                        receiver.getUsername() + " modifies a message",
                        LocalDateTime.now(),
                        NotificationType.MESSAGE_UPDATED));
    }

    @Transactional
    public void deleteMessage(Long messageId, Long userId){
        Optional<Contact> optionalSender = contactRepository.findById(userId);
        Contact me = optionalSender.orElseThrow(() -> new RuntimeException("Contact not found"));

        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        Message message = optionalMessage.orElseThrow(()-> new RuntimeException("Message not found"));

        Optional<Chat> optionalChat = chatRepository.findById(message.getChatId());
        Chat chat = optionalChat.orElseThrow(() -> new RuntimeException("Chat not found"));


        if(!me.equals(message.getSender())){
            throw new RuntimeException("You cannot delete message you didn't send");
        }

        messageRepository.delete(message);
        Contact receiver = me.equals(chat.getSender()) ? chat.getReceiver() : chat.getSender();
        notificationService.sendMessageToUser(
                receiver.getUsername(),
                new Notification(
                        receiver.getUsername() + " delete a message",
                        LocalDateTime.now(),
                        NotificationType.MESSAGE_DELETED));


    }
}
