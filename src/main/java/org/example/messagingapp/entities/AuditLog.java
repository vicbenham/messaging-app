package org.example.messagingapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.messagingapp.enums.MessageStatus;
import org.example.messagingapp.enums.MessageType;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuditLog {

    @Id
    @GeneratedValue
    private Long id;
    private String content;
    private LocalDateTime sentAt;
    @ManyToOne
    private Contact sender;
    private Boolean isEdited;
    private Long chatId;
    private MessageType type;
    private MessageStatus status;

}