package org.example.messagingapp.services;

import org.example.messagingapp.dtos.SendFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class FileService {

    @Value("${messagingApp.uploads.location}")
    private String uploadDir;

    @Transactional
    public void uploadFile(SendFile input) {

        //upload, create and save message, notify and logs
        if ((input.file() != null)) {
            MultipartFile file = input.file();
            String baseName = UUID.randomUUID().toString();
            String fileName = baseName
                    + file.getOriginalFilename();
            store(file, fileName);
        }
    }
    private void store(MultipartFile file,
                       String fileName) {
        Path uploadedPath = Paths.get(uploadDir);
        Path target = uploadedPath.resolve(fileName);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
