package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(@RequestParam("id") long id,
                            @RequestBody MultipartFile multipartFile) throws IOException {
        avatarService.uploadImage(id, multipartFile);
    }

    @GetMapping("/get/from-db")
    public ResponseEntity<byte[]> getAvatarFromDb(@RequestParam("id") long id){
        Avatar avatar = avatarService.getAvatarFromDb(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                .body(avatar.getData());
    }

    @GetMapping(path = "/get/from-local", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getAvatarFromLocal(@RequestParam("id") long id){
        return avatarService.getAvatarFromLocal(id);
    }

    @GetMapping("/get/all-avatars")
    public List<Avatar> getAllAvatars(@RequestParam("page") Integer pageNumber,
                                      @RequestParam("size") Integer pageSize){
        return avatarService.getAllAvatars(pageNumber, pageSize);
    }

}
