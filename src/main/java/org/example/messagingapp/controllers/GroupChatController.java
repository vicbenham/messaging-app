package org.example.messagingapp.controllers;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.CreateGroupChat;
import org.example.messagingapp.services.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chats/groups")
@AllArgsConstructor
public class GroupChatController {

    private final ChatService chatService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createGroup(@RequestBody CreateGroupChat request, @RequestHeader("token") Long ownerId) {
        chatService.createGroupChat(request, ownerId);
    }
}

