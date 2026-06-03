package org.example.messagingapp.dtos;

import org.example.messagingapp.enums.ChatStatus;

import java.time.LocalDateTime;

public record ChatView(Long id,
                       String contactUsername,
                       String contactEmail,
                       ChatStatus status,
                       LocalDateTime updatedAt,
                       Boolean isRead) {
}
