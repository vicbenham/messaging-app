package org.example.messagingapp.services;

import org.example.messagingapp.entities.Chat;
import org.example.messagingapp.entities.Message;
import org.example.messagingapp.entities.User;

import java.util.List;
import java.util.UUID;

public class ChatService {
    // MessageRepository messageRepository;
    public void send(Message message, UUID chatId){}

    public List<Message> getHistory(UUID chatId){
        return null;
    }

    public Chat findOrCreateChat(User sender, User receiver){
        return null;
    }

}
