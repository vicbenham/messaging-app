package org.example.messagingapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.messagingapp.enums.MessageType;

import java.rmi.server.UID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Message {
    private UUID id = UUID.randomUUID();
    private String content;
    private List<String> attachments;
    private LocalDateTime sentAt;
    @ManyToOne
    private User sender;
    private Boolean isEdited = false;
    private UUID chatId;
    private MessageType type;
}
