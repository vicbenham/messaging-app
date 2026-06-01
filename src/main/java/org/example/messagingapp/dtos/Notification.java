package org.example.messagingapp.dtos;

import org.example.messagingapp.enums.NotificationType;

import java.time.LocalDateTime;

public record Notification(String message, LocalDateTime sendAt, NotificationType type) {
}
