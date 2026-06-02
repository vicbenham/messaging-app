package org.example.messagingapp.controllers;

import org.example.messagingapp.dtos.ChatView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {

    @GetMapping
    public List<ChatView> listAllForOneUser(@RequestHeader("token") Long userId) {
        System.out.println(userId);
        return null;

    }
}
