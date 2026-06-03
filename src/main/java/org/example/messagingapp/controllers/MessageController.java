package org.example.messagingapp.controllers;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.EditMessage;
import org.example.messagingapp.dtos.MessageView;
import org.example.messagingapp.dtos.SendMessage;
import org.example.messagingapp.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{chatId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageView> getConversation(@PathVariable Long chatId, @RequestHeader("token") Long userId){
        return messageService.getConversation(chatId, userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(@RequestBody SendMessage request, @RequestHeader("token") Long userId) {
        messageService.sendMessage(request, userId);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editMessage(@RequestBody EditMessage request, @RequestHeader("token") Long userId){
        messageService.editMessage(request, userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessage(@PathVariable Long id, @RequestHeader("token") Long userId){
        messageService.deleteMessage(id, userId);
    }
}
