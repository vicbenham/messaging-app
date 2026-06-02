package org.example.messagingapp.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
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
public class Contact {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
}
