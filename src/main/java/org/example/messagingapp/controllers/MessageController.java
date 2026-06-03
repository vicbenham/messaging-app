package org.example.messagingapp.controllers;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.EditMessage;
import org.example.messagingapp.dtos.SendMessage;
import org.example.messagingapp.services.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

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
