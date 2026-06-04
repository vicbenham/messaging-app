package org.example.messagingapp.services;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.ChatView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    private final FriendshipService friendshipService;

    public List<ChatView> listAllForOneUser(Long userId) {
        return friendshipService.listAllFriendsForOneUser(userId);
    }
}
