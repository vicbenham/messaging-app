package org.example.messagingapp.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class User {
    private UUID id = UUID.randomUUID();
    private String username;
    private String password;
    private String email;
}
