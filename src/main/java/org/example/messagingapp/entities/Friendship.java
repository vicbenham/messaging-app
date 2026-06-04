package org.example.messagingapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.messagingapp.enums.ChatStatus;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user1_id", "user2_id"}))
public class Friendship {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Contact user1;
    @ManyToOne
    private Contact user2;
    private final LocalDateTime createdAt = LocalDateTime.now();
    @Setter
    private ChatStatus status;

    public boolean equals(Object other) {

        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Friendship friendship = (Friendship) other;
        return this.id.equals(friendship.getId());
    }
}

