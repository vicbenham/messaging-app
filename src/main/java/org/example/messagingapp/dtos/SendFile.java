package org.example.messagingapp.dtos;

import org.springframework.web.multipart.MultipartFile;

public record SendFile(MultipartFile file, String chatId) {
}

