package org.example.messagingapp.services;

import org.example.messagingapp.dtos.AuditLogView;
import org.example.messagingapp.entities.AuditLog;
import org.example.messagingapp.entities.Message;
import org.example.messagingapp.repositories.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void logMessage(Message message) {

        AuditLog log = AuditLog.builder()
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .sender(message.getSender())
                .type(message.getType())
                .chatId(message.getChatId())
                .isEdited(message.getIsEdited())
                .status(message.getStatus())
                .build();

        auditLogRepository.save(log);
    }

    public List<AuditLogView> listAllLogs() {
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        List<AuditLogView> result = new ArrayList<>();

        auditLogs.forEach(log -> {
            AuditLogView toSave = new AuditLogView(
                    log.getId(),
                    log.getContent(),
                    log.getSentAt(),
                    log.getSender().getEmail(),
                    log.getIsEdited(),
                    log.getChatId(),
                    log.getType(),
                    log.getStatus()
            );
            result.add(toSave);
        });
        return result;
    }
}