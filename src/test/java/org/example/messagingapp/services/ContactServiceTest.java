package org.example.messagingapp.services;

import org.example.messagingapp.dtos.Signin;
import org.example.messagingapp.dtos.Signup;
import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.exceptions.ForbiddenException;
import org.example.messagingapp.repositories.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Captor
    private ArgumentCaptor<Contact> contactCaptor;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void signup_shouldSaveContactWithProvidedFields() {
        Signup request = new Signup("toto", "123", "toto@gmail.com");

        contactService.signup(request);

        verify(contactRepository, times(1)).save(contactCaptor.capture());
        Contact saved = contactCaptor.getValue();

        assertEquals("toto", saved.getUsername());
        assertEquals("123", saved.getPassword());
        assertEquals("toto@gmail.com", saved.getEmail());
    }

    @Test
    void signin_shouldReturnContactIdWhenCredentialsAreCorrect() {
        Contact stored = Contact.builder()
                .id(42L)
                .username("toto")
                .password("123")
                .email("toto@gmail.com")
                .build();

        Signin request = new Signin("toto@gmail.com", "123");

        when(contactRepository.findContactByEmail("toto@gmail.com"))
                .thenReturn(Optional.of(stored));

        Long resultId = contactService.signin(request);

        assertNotNull(resultId);
        assertEquals(42L, resultId);
        verify(contactRepository, times(1)).findContactByEmail("toto@gmail.com");
    }

    @Test
    void signin_shouldThrowForbiddenWhenEmailNotFound() {
        Signin request = new Signin("unknown@gmail.com", "000");

        when(contactRepository.findContactByEmail("unknown@gmail.com"))
                .thenReturn(Optional.empty());

        ForbiddenException ex = assertThrows(ForbiddenException.class, () -> contactService.signin(request));
        assertTrue(ex.getMessage().contains("Wrong credentials"));
        verify(contactRepository, times(1)).findContactByEmail("unknown@gmail.com");
    }

    @Test
    void signin_shouldThrowForbiddenWhenPasswordIncorrect() {
        Contact stored = Contact.builder()
                .id(10L)
                .username("toto")
                .password("correctPassword")
                .email("toto@gmail.com")
                .build();

        Signin request = new Signin("toto@gmail.com", "wrongPassword");

        when(contactRepository.findContactByEmail("toto@gmail.com"))
                .thenReturn(Optional.of(stored));

        ForbiddenException ex = assertThrows(ForbiddenException.class, () -> contactService.signin(request));
        assertTrue(ex.getMessage().contains("Wrong credentials"));
        verify(contactRepository, times(1)).findContactByEmail("toto@gmail.com");
    }
}
