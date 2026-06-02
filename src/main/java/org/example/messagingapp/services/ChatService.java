package org.example.messagingapp.services;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.ChatView;
import org.example.messagingapp.entities.Chat;
import org.example.messagingapp.entities.Contact;
import org.example.messagingapp.entities.Message;
import org.example.messagingapp.repositories.ChatRepository;
import org.example.messagingapp.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatService {

    private final ContactRepository contactRepository;
    private final ChatRepository chatRepository;

    public void send(Message message, UUID chatId){}

    public List<Message> getHistory(UUID chatId){
        return null;
    }

    public Chat findOrCreateChat(Contact sender, Contact receiver){
        return null;
    }

    public List<ChatView> listAllForOneUser(Long userId){
        Optional<Contact> optionalContact = contactRepository.findById(userId);
        Contact contact = optionalContact.orElseThrow(() -> new RuntimeException("Contact not found"));
        List<Chat> chats = chatRepository.findAllBySenderOrReceiver(contact, contact);
        List<ChatView> results = new ArrayList<>();
        chats.stream().forEach(chat -> {
            Contact receiver = chat.getSender().equals(contact.getUsername()) ? chat.getReceiver() : chat.getSender();
            ChatView chatViewEl = new ChatView(
                    receiver.getEmail(),
                    receiver.getUsername(),
                    chat.getStatus(),
                    chat.getCreatedAt(),
                    true);
            results.add(chatViewEl);
        });
        return results;
    }

}
