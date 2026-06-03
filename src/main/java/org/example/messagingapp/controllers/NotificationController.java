package org.example.messagingapp.controllers;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.Notification;
import org.example.messagingapp.enums.NotificationType;
import org.example.messagingapp.services.NotificationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Notification> test(@RequestParam String user){
        notificationService.addChannel(user);
        return notificationService.useUserChannel(user);
    }

    @GetMapping("/fill")
    public ResponseEntity fill(){
        Notification notification = new Notification(
                "toto",
                LocalDateTime.now(),
                NotificationType.NEW_MESSAGE);
        try {
            this.notificationService.sendMessageToUser("toto", notification);
            return ResponseEntity.ok("Message sent with success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/unsubscribe")
    public void unsubscribe(@RequestParam String userName){
        this.notificationService.unsubscribe(userName);
    }
}
