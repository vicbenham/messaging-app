package org.example.messagingapp.controllers;

import org.example.messagingapp.dtos.ContactDto;
import org.example.messagingapp.services.ContactService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public void create(@RequestBody ContactDto request) {
        contactService.create(request);
    }
}

