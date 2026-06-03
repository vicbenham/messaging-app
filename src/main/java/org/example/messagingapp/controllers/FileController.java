package org.example.messagingapp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.SendFile;
import org.example.messagingapp.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class FileController {

    private final FileService fileService;
    @PostMapping("/files")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void sendFile(
            @ModelAttribute @Valid SendFile input, @RequestHeader("token") Long userId) {
        fileService.uploadFile(input, userId);
    }
}
