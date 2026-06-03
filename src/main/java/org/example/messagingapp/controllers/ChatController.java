package org.example.messagingapp.controllers;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.ChatView;
import org.example.messagingapp.dtos.FriendRequest;
import org.example.messagingapp.services.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chats")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public List<ChatView> listAllForOneUser(@RequestHeader("token") Long userId) {
        return chatService.listAllForOneUser(userId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void requestFriend(@RequestBody FriendRequest request, @RequestHeader("token") Long userId){
        chatService.requestFriend(request, userId);
    }

    @PatchMapping("/{chatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acceptFriendRequest(@RequestHeader("token") Long userId, @PathVariable Long chatId) {
         chatService.acceptFriendRequest(userId, chatId);
    }

    @DeleteMapping("/{chatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void declineFriendRequest(@RequestHeader("token") Long userId, @PathVariable Long chatId) {
        chatService.declineFriendRequest(userId, chatId);
    }
}
