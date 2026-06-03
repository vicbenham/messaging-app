package org.example.messagingapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.example.messagingapp.enums.MessageStatus;
import org.example.messagingapp.enums.MessageType;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @Setter
    private String content;
    private LocalDateTime sentAt;
    @ManyToOne
    private Contact sender;
    @Setter
    private Boolean isEdited = false;
    private Long chatId;
    private MessageType type;
    private MessageStatus status;
}
