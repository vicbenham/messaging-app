package org.example.messagingapp.controllers;

import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.example.messagingapp.dtos.Notification;
import org.example.messagingapp.enums.NotificationType;
import org.example.messagingapp.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Notification> test(){
        return notificationService.getFlux();
    }

    @GetMapping("/fill")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fill(){
        Notification notification = new Notification(
                "toto",
                LocalDateTime.now(),
                NotificationType.NEW_MESSAGE);
        this.notificationService.addMessage(notification);
    }
}
