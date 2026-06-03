package org.example.messagingapp.dtos;

import org.example.messagingapp.validations.ChatExist;
import org.example.messagingapp.validations.FileSize;
import org.springframework.web.multipart.MultipartFile;

public record SendFile(@FileSize MultipartFile file, @ChatExist Long chatId) {
}

