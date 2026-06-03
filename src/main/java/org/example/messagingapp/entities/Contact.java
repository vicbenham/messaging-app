package org.example.messagingapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
