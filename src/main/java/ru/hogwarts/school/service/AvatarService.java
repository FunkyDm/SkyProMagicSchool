package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    public void uploadImage(long id, MultipartFile multipartFile) throws IOException;

    Avatar getAvatarFromDb(long id);

    byte[] getAvatarFromLocal(long id);

    List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize);

}
