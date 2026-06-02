package org.example.messagingapp.services;

import org.example.messagingapp.dtos.ContactDto;
import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void create(ContactDto request) {
        Contact contact = Contact.builder()
                .id(UUID.randomUUID())
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();

        contactRepository.save(contact);
    }

}

