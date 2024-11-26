package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.MissingAvatarException;
import ru.hogwarts.school.exception.MissingStudentException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class AvatarServiceImpl implements ru.hogwarts.school.service.AvatarService {
    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    @Value("${path.dir}")
    private Path pathDir;

    private final StudentRepository studentRepository;

    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public void uploadImage(long id, MultipartFile multipartFile) throws IOException {

        createDirectory();

        Path filePath = pathDir.resolve(UUID.randomUUID() + "." + getExtension(multipartFile.getOriginalFilename()));

        createAvatar(filePath, multipartFile, id);

        logger.debug("Image was uploading to directory");
        multipartFile.transferTo(filePath);

    }

    @Override
    public Avatar getAvatarFromDb(long id) {
        checkStudentExistById(id);

        logger.info("Was invoked method for getting avatar from db");
        return avatarRepository.getByStudentId(id)
                .orElseThrow(() -> new MissingAvatarException(id));
    }

    @Override
    public byte[] getAvatarFromLocal(long id){
        checkStudentExistById(id);

        Avatar avatar = avatarRepository.getByStudentId(id)
                .orElseThrow(() -> {
                    logger.error("There is not avatar with id = {}", id);
                    return new MissingAvatarException(id);
                });
        String filePath = avatar.getFilePath();
        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath))){
            logger.debug("Чтение картинки");
            return bufferedInputStream.readAllBytes();
        } catch (IOException e){
            throw new IllegalArgumentException("Чтение картинки не удалось" + e.getMessage());
        }
    }

    @Override
    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    private void createAvatar(Path filePath, MultipartFile multipartFile, long id) throws IOException {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("There is not student with id = {}", id);
                    return new MissingStudentException(id);
                });

        logger.info("Was invoked method for creating avatar");
        avatarRepository.save(new Avatar(
                filePath.toString(),
                multipartFile.getSize(),
                multipartFile.getContentType(),
                multipartFile.getBytes(),
                student
        ));
    }

    private String getExtension(String originalPath) {
        return originalPath.substring(originalPath.lastIndexOf(".") + 1);
    }

    private void createDirectory() throws IOException {
        Path path = pathDir;
        if (Files.notExists(path)) {
            logger.info("Was invoked method for creating directory for images");
            Files.createDirectories(path);
        }
    }

    private void checkStudentExistById(long id) {
        boolean studentExist = studentRepository.existsById(id);
        if (!studentExist) {
            logger.error("There is not student with id = {}", id);
            throw new MissingStudentException(id);
        }
    }

}
