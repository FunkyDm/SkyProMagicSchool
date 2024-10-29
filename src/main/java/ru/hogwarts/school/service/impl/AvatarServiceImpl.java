package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.UUID;

@Service
public class AvatarServiceImpl {
    private String pathDir = "images";

    public void uploadImage(long id, MultipartFile multipartFile) throws IOException{
        createDirectory();

        Path filePath = Path.of(pathDir, UUID.randomUUID() + "." + getExtension(multipartFile.getOriginalFilename()));



    }

    private String getExtension(String originalPath){
        return originalPath.substring(originalPath.lastIndexOf(".") + 1);
    }

    private void createDirectory() throws IOException {
        Path path = Path.of(pathDir);
        if(Files.notExists(path)){
            Files.createDirectories(path);
        }
    }
}
