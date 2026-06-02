package org.example.messagingapp.repositories;

import org.aspectj.apache.bcel.classfile.Module;
import org.example.messagingapp.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findContactByEmail(String email);
}

