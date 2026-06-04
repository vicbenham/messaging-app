package org.example.messagingapp.dtos;

import org.example.messagingapp.enums.MessageStatus;
import org.example.messagingapp.enums.MessageType;

import java.time.LocalDateTime;

public record AuditLogView(
        Long id,
        String content,
        LocalDateTime sentAt,
        String senderEmail,
        Boolean isEdited,
        Long chatId,
        MessageType type,
        MessageStatus status
) {
}
