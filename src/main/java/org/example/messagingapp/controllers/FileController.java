package org.example.messagingapp.controllers;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.SendFile;
import org.example.messagingapp.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FileController {

    private final FileService fileService;
    @PostMapping("/files")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateAvatar(
            @ModelAttribute SendFile input) {
        fileService.uploadFile(input);
    }
}
