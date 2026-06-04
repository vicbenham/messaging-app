package org.example.messagingapp.services;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.ChatView;
import org.example.messagingapp.dtos.FriendRequest;
import org.example.messagingapp.dtos.Notification;
import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.entities.Friendship;
import org.example.messagingapp.enums.ChatStatus;
import org.example.messagingapp.enums.NotificationType;
import org.example.messagingapp.repositories.ContactRepository;
import org.example.messagingapp.repositories.FriendshipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

//ajout
import org.example.messagingapp.exceptions.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class FriendshipService {

    private final ContactRepository contactRepository;
    private final FriendshipRepository friendshipRepository;
    private final NotificationService notificationService;

    public List<ChatView> listAllFriendsForOneUser(Long userId) {

        Optional<Contact> optionalContact = contactRepository.findById(userId);
        Contact me = optionalContact.orElseThrow(() -> new NotFoundException("Contact not found"));

        Collection<Friendship> friendships = friendshipRepository.findAllByUser1OrUser2(me);
        List<ChatView> results = new ArrayList<>();

        friendships.forEach(friendship -> {
            Contact contact = friendship.getUser1().getUsername().equals(me.getUsername())
                    ? friendship.getUser2()
                    : friendship.getUser1();

            ChatView chatViewEl = new ChatView(
                    friendship.getId(),
                    contact.getUsername(),
                    contact.getEmail(),
                    friendship.getStatus(),
                    friendship.getCreatedAt(),
                    true);
            results.add(chatViewEl);
        });
        return results;
    }

    @Transactional
    public void requestFriend(FriendRequest request, Long userId) {

        Optional<Contact> optionalUser1 = contactRepository.findById(userId);
        Contact user1 = optionalUser1.orElseThrow(
                () -> new NotFoundException("Contact not found"));
//        Contact user1 = optionalUser1.orElseThrow(() -> new RuntimeException("Sender not found"));
        Optional<Contact> optionalUser2 = contactRepository.findContactByEmail(request.email());
        Contact user2 = optionalUser2.orElseThrow(
                () -> new NotFoundException("Receiver not found"));
//        Contact user2 = optionalUser2.orElseThrow(() -> new RuntimeException("Receiver not found"));

        Friendship friendship = Friendship.builder()
                .status(user1.getId().equals(user2.getId()) ? ChatStatus.ACCEPTED : ChatStatus.PENDING)
                .user1(user1)
                .user2(user2)
                .build();

        friendshipRepository.save(friendship);
        notificationService.sendMessageToUser(user2.getUsername(), new Notification(
                user1.getUsername() + " wants to be your friend",
                LocalDateTime.now(),
                NotificationType.PENDING_REQUEST));
    }

    @Transactional
    public void acceptFriendRequest(@RequestHeader("token") Long userId, @PathVariable Long friendshipId) {

        Optional<Contact> optionalContact = contactRepository.findById(userId);
        Contact me = optionalContact.orElseThrow(() -> new NotFoundException("Contact not found"));
        Optional<Friendship> optionalFriendship = friendshipRepository.findById(friendshipId);
        Friendship friendship = optionalFriendship.orElseThrow(() -> new NotFoundException("Friendship not found"));

        if (!friendship.getStatus().equals(ChatStatus.PENDING)) {
            throw new ConflictException("Friendship status is not PENDING");
        }

        if (!friendship.getUser2().equals(me)) {
            throw new  ForbiddenException("You are not the receiver of this friendship request");
        }

        friendship.setStatus(ChatStatus.ACCEPTED);
        friendshipRepository.save(friendship);
        notificationService.sendMessageToUser(
                friendship.getUser1().getUsername(),
                new Notification(
                        me.getUsername() + " is now your friend",
                        LocalDateTime.now(),
                        NotificationType.ACCEPTED_REQUEST));
    }

    @Transactional
    public void declineFriendRequest(@RequestHeader("token") Long userId, @PathVariable Long friendshipId) {
        Optional<Contact> optionalContact = contactRepository.findById(userId);
        Contact me = optionalContact.orElseThrow(() -> new NotFoundException("Contact not found"));

        Optional<Friendship> optionalFriendship = friendshipRepository.findById(friendshipId);
        Friendship friendship = optionalFriendship.orElseThrow(() -> new NotFoundException("Friendship not found"));

        if (!friendship.getStatus().equals(ChatStatus.PENDING)) {
            throw new ConflictException("Friendship status is not PENDING");
        }
        if (!friendship.getUser2().equals(me)) {
            throw new ForbiddenException("You are not the receiver of this friendship request");
        }

        friendshipRepository.delete(friendship);
        notificationService.sendMessageToUser(
                friendship.getUser1().getUsername(),
                new Notification(
                        me.getUsername() + " declined your friend request",
                        LocalDateTime.now(),
                        NotificationType.DECLINED_REQUEST));
    }
}


