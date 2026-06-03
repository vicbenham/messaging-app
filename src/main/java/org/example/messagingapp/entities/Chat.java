package org.example.messagingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.messagingapp.enums.ChatStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"sender_id", "receiver_id"}))
/*
    CAREFUL => CUSTOM VALIDATION NEEDED TO
    VALIDATED SENDER/RECEIVER
 */
public class Chat {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Contact sender;
    @ManyToOne
    private Contact receiver;
    private final LocalDateTime createdAt = LocalDateTime.now();
    @Setter
    private ChatStatus status;
}
