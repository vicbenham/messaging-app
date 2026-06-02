package org.example.messagingapp.controllers;

import org.example.messagingapp.dtos.FriendRequest;
import org.example.messagingapp.dtos.Signin;
import org.example.messagingapp.dtos.Signup;
import org.example.messagingapp.services.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody Signup request) {
        contactService.signup(request);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public Long signin(@RequestBody Signin request){
        return contactService.signin(request);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void requestFriend(@RequestBody FriendRequest request, @RequestHeader("token") Long userId){
        contactService.requestFriend(request, userId);
    }
}

