package org.example.messagingapp.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;

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

    public boolean equals(Object other) {

        // Vérification de l'adresse mémoire
        if(this == other) return true;
        // Check non null ou classe différente
        if(other == null || getClass() != other.getClass()) return false;
        // Type casting parce qu'on est sur que other est un Contact
        Contact contact = (Contact) other;

        return this.id.equals(contact.getId());
    }
}
