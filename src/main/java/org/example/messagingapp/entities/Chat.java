package org.example.messagingapp.entities;

import jakarta.persistence.Entity;
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
public class Chat {
    @Id
    private UUID id = UUID.randomUUID();
    @OneToOne
    private Contact sender;
    @OneToOne
    private Contact receiver;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private ChatStatus status;
}
