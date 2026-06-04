package org.example.messagingapp.entities;

import jakarta.persistence.*;
import org.example.messagingapp.enums.AuditActionType;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuditActionType actionType;

    private String sender;

    private String receiver;

    private String details;

    private LocalDateTime createdAt;

    public AuditLog() {
    }

    public AuditLog(
            AuditActionType actionType,
            String sender,
            String receiver,
            String details
    ) {
        this.actionType = actionType;
        this.sender = sender;
        this.receiver = receiver;
        this.details = details;
        this.createdAt = LocalDateTime.now();
    }
}