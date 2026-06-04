package org.example.messagingapp.services;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.EditMessage;
import org.example.messagingapp.dtos.MessageView;
import org.example.messagingapp.dtos.Notification;
import org.example.messagingapp.dtos.SendMessage;
import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.entities.Friendship;
import org.example.messagingapp.entities.Message;
import org.example.messagingapp.enums.ChatStatus;
import org.example.messagingapp.enums.MessageStatus;
import org.example.messagingapp.enums.MessageType;
import org.example.messagingapp.enums.NotificationType;
import org.example.messagingapp.repositories.ContactRepository;
import org.example.messagingapp.repositories.FriendshipRepository;
import org.example.messagingapp.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final ContactRepository contactRepository;
    private final FriendshipRepository friendshipRepository;
    private final MessageRepository messageRepository;
    private final NotificationService notificationService;

    @Transactional
    public void sendMessage(SendMessage request, Long userId) {
        Optional<Contact> optionalSender = contactRepository.findById(userId);
        Contact me = optionalSender.orElseThrow(() -> new RuntimeException("Contact not found"));

        Optional<Friendship> optionalFriendship = friendshipRepository.findById(request.chatId());
        Friendship friendship = optionalFriendship.orElseThrow(() -> new RuntimeException("Friendship not found"));

        if (!friendship.getUser1().getId().equals(me.getId()) && !friendship.getUser2().getId().equals(me.getId())) {
            throw new RuntimeException("Contact is not part of this friendship");
        }

        if (!friendship.getStatus().equals(ChatStatus.ACCEPTED)) {
            throw new RuntimeException("Friendship status is not ACCEPTED");
        }

        Message message = Message.builder()
                .content(request.content())
                .sender(me)
                .chatId(request.chatId())
                .status(MessageStatus.SENT)
                .sentAt(LocalDateTime.now())
                .type(MessageType.TEXT)
                .build();
        messageRepository.save(message);

        Contact receiver = me.equals(friendship.getUser1()) ? friendship.getUser2() : friendship.getUser1();
        notificationService.sendMessageToUser(
                receiver.getUsername(),
                new Notification(
                        me.getUsername() + " sent you message",
                        LocalDateTime.now(),
                        NotificationType.NEW_MESSAGE));
    }

    @Transactional
    public void editMessage(EditMessage request, Long userId) {
        Optional<Contact> optionalSender = contactRepository.findById(userId);
        Contact me = optionalSender.orElseThrow(() -> new RuntimeException("Contact not found"));

        Optional<Friendship> optionalFriendship = friendshipRepository.findById(request.chatId());
        Friendship friendship = optionalFriendship.orElseThrow(() -> new RuntimeException("Friendship not found"));

        if (!friendship.getUser1().getId().equals(me.getId()) && !friendship.getUser2().getId().equals(me.getId())) {
            throw new RuntimeException("User is not part of this friendship");
        }

        if (!friendship.getStatus().equals(ChatStatus.ACCEPTED)) {
            throw new RuntimeException("Friendship status is not ACCEPTED");
        }

        Optional<Message> optionalMessage = messageRepository.findById(request.messageId());
        Message message = optionalMessage.orElseThrow(() -> new RuntimeException("Message not found"));

        if (!message.getSender().equals(me)) {
            throw new RuntimeException("You cannot edit a message you didn't write");
        }
        if (!message.getChatId().equals(friendship.getId())) {
            throw new RuntimeException("This message is not in this right friendship");
        }

        message.setIsEdited(true);
        message.setContent(request.content());

        messageRepository.save(message);

        Contact receiver = me.equals(friendship.getUser1()) ? friendship.getUser2() : friendship.getUser1();
        notificationService.sendMessageToUser(
                receiver.getUsername(),
                new Notification(
                        me.getUsername() + " modifies a message",
                        LocalDateTime.now(),
                        NotificationType.MESSAGE_UPDATED));
    }

    @Transactional
    public void deleteMessage(Long messageId, Long userId) {
        Optional<Contact> optionalSender = contactRepository.findById(userId);
        Contact me = optionalSender.orElseThrow(() -> new RuntimeException("Contact not found"));

        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        Message message = optionalMessage.orElseThrow(() -> new RuntimeException("Message not found"));

        Optional<Friendship> optionalFriendship = friendshipRepository.findById(message.getChatId());
        Friendship friendship = optionalFriendship.orElseThrow(() -> new RuntimeException("Friendship not found"));

        if (!me.equals(message.getSender())) {
            throw new RuntimeException("You cannot delete message you didn't send");
        }

        messageRepository.delete(message);
        Contact receiver = me.equals(friendship.getUser1()) ? friendship.getUser2() : friendship.getUser1();
        notificationService.sendMessageToUser(
                receiver.getUsername(),
                new Notification(
                        me.getUsername() + " delete a message",
                        LocalDateTime.now(),
                        NotificationType.MESSAGE_DELETED));
    }

    public List<MessageView> getConversation(Long chatId, Long userId) {
        Optional<Contact> optionalSender = contactRepository.findById(userId);
        Contact me = optionalSender.orElseThrow(() -> new RuntimeException("Contact not found"));

        Optional<Friendship> optionalFriendship = friendshipRepository.findById(chatId);
        Friendship friendship = optionalFriendship.orElseThrow(() -> new RuntimeException("Friendship not found"));

        if (!me.equals(friendship.getUser1()) && !me.equals(friendship.getUser2())) {
            throw new RuntimeException("You cannot see this friendship");
        }

        Collection<Message> messages = messageRepository.findAllByChatIdOrderBySentAt(chatId, Message.class);
        List<MessageView> views = new ArrayList<>();
        messages.forEach((message -> {
            MessageView view = new MessageView(
                    message.getContent(),
                    message.getSender().getUsername(),
                    message.getSentAt(),
                    message.getType(),
                    message.getStatus()
            );
            views.add(view);
        }));
        return views;
    }
}
