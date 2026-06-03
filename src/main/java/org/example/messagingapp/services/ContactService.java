package org.example.messagingapp.services;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.FriendRequest;
import org.example.messagingapp.dtos.Notification;
import org.example.messagingapp.dtos.Signin;
import org.example.messagingapp.dtos.Signup;
import org.example.messagingapp.entities.Chat;
import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.enums.ChatStatus;
import org.example.messagingapp.enums.NotificationType;
import org.example.messagingapp.repositories.ChatRepository;
import org.example.messagingapp.repositories.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ChatRepository chatRepository;
    private final NotificationService notificationService;

    @Transactional
    public void signup(Signup request) {
        Contact contact = Contact.builder()
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .build();

        contactRepository.save(contact);
    }

    public Long signin(Signin request){
        Optional<Contact> optionalContact = contactRepository.findContactByEmail(request.email());
        Contact contact = optionalContact.orElseThrow(() -> new RuntimeException("Wrong credentials"));
        if(!contact.getPassword().equals(request.password())){
            throw new RuntimeException("Wrong credentials");
        }

        return contact.getId();
    }
}

