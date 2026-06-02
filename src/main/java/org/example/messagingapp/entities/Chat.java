package org.example.messagingapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.messagingapp.enums.ChatStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
/*
    CAREFUL => CUSTOM VALIDATION NEEDED TO
    VALIDATED SENDER/RECEIVER
 */
public class Chat {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Contact sender;
    @OneToOne
    private Contact receiver;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private ChatStatus status;
}
