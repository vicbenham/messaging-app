package org.example.messagingapp.controllers;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.ChatView;
import org.example.messagingapp.services.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
