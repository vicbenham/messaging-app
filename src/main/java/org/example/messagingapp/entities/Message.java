package org.example.messagingapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.messagingapp.enums.MessageStatus;

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
    private String content;
    private LocalDateTime sentAt;
    @ManyToOne
    private Contact sender;
    private Boolean isEdited = false;
    private Long chatId;
    private MessageStatus status;
}
