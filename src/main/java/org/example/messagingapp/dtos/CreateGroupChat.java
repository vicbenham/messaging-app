package org.example.messagingapp.dtos;

import java.util.List;

public record CreateGroupChat(String name, List<Long> participantsIds) {
}

