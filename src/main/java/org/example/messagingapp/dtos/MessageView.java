package org.example.messagingapp.dtos;

import org.example.messagingapp.enums.MessageStatus;
import org.example.messagingapp.enums.MessageType;

import java.time.LocalDateTime;

public record MessageView(
        String content,
        String senderName,
        LocalDateTime sendAt,
        MessageType type,
        MessageStatus status) {
}
