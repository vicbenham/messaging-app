package org.example.messagingapp.controllers;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.ChatView;
import org.example.messagingapp.dtos.FriendRequest;
import org.example.messagingapp.services.FriendshipService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendships")
@AllArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @GetMapping
    public List<ChatView> listAllFriendsForOneUser(@RequestHeader("token") Long userId) {
        return friendshipService.listAllFriendsForOneUser(userId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void requestFriend(@RequestBody FriendRequest request, @RequestHeader("token") Long userId) {
        friendshipService.requestFriend(request, userId);
    }

    @PatchMapping("/{friendshipId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acceptFriendRequest(@RequestHeader("token") Long userId, @PathVariable Long friendshipId) {
        friendshipService.acceptFriendRequest(userId, friendshipId);
    }

    @DeleteMapping("/{friendshipId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void declineFriendRequest(@RequestHeader("token") Long userId, @PathVariable Long friendshipId) {
        friendshipService.declineFriendRequest(userId, friendshipId);
    }
}

