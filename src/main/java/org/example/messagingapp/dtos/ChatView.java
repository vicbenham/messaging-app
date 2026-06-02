package org.example.messagingapp.dtos;

import org.example.messagingapp.enums.ChatStatus;

import java.time.LocalDateTime;

public record ChatView(String receiverUsername,
                       String receiverEmail,
                       ChatStatus status,
                       LocalDateTime updatedAt,
                       Boolean isRead) {
}
