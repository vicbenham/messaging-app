package org.example.messagingapp.services;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.ChatView;
import org.example.messagingapp.dtos.CreateGroupChat;
import org.example.messagingapp.entities.Chat;
import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.enums.ChatStatus;
import org.example.messagingapp.repositories.ChatRepository;
import org.example.messagingapp.repositories.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final FriendshipService friendshipService;
    private final ChatRepository chatRepository;
    private final ContactRepository contactRepository;

    public List<ChatView> listAllForOneUser(Long userId) {

        List<ChatView> results = friendshipService.listAllFriendsForOneUser(userId);

        // group chats
        Optional<Contact> optionalContact = contactRepository.findById(userId);
        Contact me = optionalContact.orElseThrow(() -> new RuntimeException("Contact not found"));

        List<Chat> groupChats = chatRepository.findAllByParticipantsContains(me);
        groupChats.forEach(chat -> {
            if (chat.getIsGroup()) {
                ChatView chatView = new ChatView(
                        chat.getId(),
                        chat.getGroupName(),
                        me.getEmail(),
                        chat.getStatus(),
                        chat.getCreatedAt(),
                        true
                );
                results.add(chatView);
            }
        });
        return results;
    }

    @Transactional
    public void createGroupChat(CreateGroupChat request, Long adminId) {

        Optional<Contact> optionalAdmin = contactRepository.findById(adminId);
        Contact admin = optionalAdmin.orElseThrow(() -> new RuntimeException("Admin not found"));

        Chat chat = Chat.builder()
                .isGroup(true)
                .groupName(request.name())
                .status(ChatStatus.ACCEPTED)
                .build();

        Set<Contact> participants = new HashSet<>();
        participants.add(admin);

        if (request.participantsIds() != null) {

            request.participantsIds().forEach(id -> {
                contactRepository.findById(id).ifPresent(participants::add);
            });
        }

        chat.setParticipants(participants);
        chatRepository.save(chat);
    }
}
