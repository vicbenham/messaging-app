package org.example.messagingapp.services;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.ChatView;
import org.example.messagingapp.dtos.FriendRequest;
import org.example.messagingapp.dtos.Notification;
import org.example.messagingapp.entities.Chat;
import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.entities.Message;
import org.example.messagingapp.enums.ChatStatus;
import org.example.messagingapp.enums.NotificationType;
import org.example.messagingapp.repositories.ChatRepository;
import org.example.messagingapp.repositories.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final ContactRepository contactRepository;
    private final ChatRepository chatRepository;
    private final NotificationService notificationService;

    public void send(Message message, UUID chatId){}

    public List<Message> getHistory(UUID chatId){
        return null;
    }

    public Chat findOrCreateChat(Contact sender, Contact receiver){
        return null;
    }



    public List<ChatView> listAllForOneUser(Long userId){

        Optional<Contact> optionalContact = contactRepository.findById(userId);
        Contact me = optionalContact.orElseThrow(() -> new RuntimeException("Contact not found"));

        Collection<Chat> chats = chatRepository.findAllBySenderOrReceiver(me, me, Chat.class);
        List<ChatView> results = new ArrayList<>();

        chats.stream().forEach(chat -> {
            Contact contact = chat.getSender().getUsername().equals(me.getUsername())
                    ? chat.getReceiver()
                    : chat.getSender();

            ChatView chatViewEl = new ChatView(
                    chat.getId(),
                    contact.getEmail(),
                    contact.getUsername(),
                    chat.getStatus(),
                    chat.getCreatedAt(),
                    true);
            results.add(chatViewEl);
        });
        return results;
    }

    @Transactional
    public void requestFriend(FriendRequest request, Long userId){
        Optional<Contact> optionalSender = contactRepository.findById(userId);
        Contact sender = optionalSender.orElseThrow(()-> new RuntimeException("Sender not found"));
        Optional<Contact> optionalReceiver = contactRepository.findContactByEmail(request.email());
        Contact receiver = optionalReceiver.orElseThrow(()-> new RuntimeException("Receiver not found"));

        Chat chat = Chat.builder()
                .status(sender.getId().equals(receiver.getId()) ? ChatStatus.ACCEPTED : ChatStatus.PENDING)
                .sender(sender)
                .receiver(receiver)
                .build();


        chatRepository.save(chat);
        Notification notification = new Notification(
                sender.getUsername() + " wants to be your friend",
                LocalDateTime.now(),
                NotificationType.PENDING_REQUEST);
        notificationService.sendMessageToUser(receiver.getUsername(), notification);
    }

    @Transactional
    public void acceptFriendRequest(@RequestHeader("token") Long userId, @PathVariable Long chatId) {

        Optional<Contact> optionalContact = contactRepository.findById(userId);
        Contact me = optionalContact.orElseThrow(() -> new RuntimeException("Contact not found"));

        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        Chat chat = optionalChat.orElseThrow(() -> new RuntimeException("Chat not found"));

        // Vérifier que la conv est PENDING
        if(!chat.getStatus().equals(ChatStatus.PENDING)) {
            throw new RuntimeException("Chat status is not PENDING");
        }

        // Vérifier que le userID n'est pas celui du receiver
        if(!chat.getReceiver().equals(me)) {
            throw new RuntimeException("Chat receiver shouldn't be sender (and be a stalker)");
        }

        chat.setStatus(ChatStatus.ACCEPTED);
        chatRepository.save(chat);
        notificationService.sendMessageToUser(
                chat.getSender().getUsername(),
                new Notification(
                        me.getUsername() + " is now your friend",
                        LocalDateTime.now(),
                        NotificationType.ACCEPTED_REQUEST));
    }

    @Transactional
    public void declineFriendRequest(@RequestHeader("token") Long userId, @PathVariable Long chatId) {
        Optional<Contact> optionalContact = contactRepository.findById(userId);
        Contact me = optionalContact.orElseThrow(() -> new RuntimeException("Contact not found"));

        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        Chat chat = optionalChat.orElseThrow(() -> new RuntimeException("Chat not found"));

        if(!chat.getStatus().equals(ChatStatus.PENDING)) {
            throw new RuntimeException("Chat status is not PENDING");
        }
        if(!chat.getReceiver().equals(me)) {
            throw new RuntimeException("Chat receiver shouldn't be sender (and be a stalker)");
        }

        chatRepository.delete(chat);
        notificationService.sendMessageToUser(
                chat.getSender().getUsername(),
                new Notification(
                        me.getUsername() + " hates you",
                        LocalDateTime.now(),
                        NotificationType.DECLINED_REQUEST));

    }

}
