package org.example.messagingapp.services;

import org.example.messagingapp.entities.AuditLog;
import org.example.messagingapp.enums.AuditActionType;
import org.example.messagingapp.repositories.AuditLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void logMessage(
            String sender,
            String receiver,
            String content
    ) {

        AuditLog log = new AuditLog(
                AuditActionType.MESSAGE,
                sender,
                receiver,
                content
        );

        auditLogRepository.save(log);
    }

    public void logFile(
            String sender,
            String receiver,
            String fileName
    ) {

        AuditLog log = new AuditLog(
                AuditActionType.FILE,
                sender,
                receiver,
                fileName
        );

        auditLogRepository.save(log);
    }
}