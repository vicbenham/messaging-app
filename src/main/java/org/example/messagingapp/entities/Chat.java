package org.example.messagingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.messagingapp.enums.ChatStatus;

import java.time.LocalDateTime;
import java.util.Set;

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

    @Setter
    private Boolean isGroup = false;
    @Setter
    private String groupName;
    @Setter
    @ManyToMany
    @JoinTable(name = "chat_participants", joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "contact_id"))
    private Set<Contact> participants;
}
