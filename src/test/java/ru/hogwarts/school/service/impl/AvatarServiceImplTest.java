package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.MissingAvatarException;
import ru.hogwarts.school.exception.MissingStudentException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestPropertySource()
class AvatarServiceImplTest {
    @InjectMocks
    private AvatarServiceImpl out;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private AvatarRepository avatarRepository;

    @Test
    void testGetAvatarFromDb_ShouldReturnAvatar_WhenStudentAndAvatarExist() {
        long studentId = 1L;
        Avatar mockAvatar = new Avatar();
        when(studentRepository.existsById(studentId)).thenReturn(true);
        when(avatarRepository.getByStudentId(studentId)).thenReturn(Optional.of(mockAvatar));

        Avatar result = out.getAvatarFromDb(studentId);

        assertEquals(mockAvatar, result);
    }

    @Test
    void testGetAvatarFromDb_ShouldThrowMissingStudentException_WhenStudentDoesNotExist() {
        long studentId = 1L;
        when(studentRepository.existsById(studentId)).thenReturn(false);

        assertThrows(MissingStudentException.class, () -> out.getAvatarFromDb(studentId));
    }

    @Test
    void testGetAvatarFromDb_ShouldThrowMissingAvatarException_WhenAvatarDoesNotExist() {
        long studentId = 1L;
        when(studentRepository.existsById(studentId)).thenReturn(true);
        when(avatarRepository.getByStudentId(studentId)).thenReturn(Optional.empty());

        assertThrows(MissingAvatarException.class, () -> out.getAvatarFromDb(studentId));

    }

    @Test
    void testGetAvatarFromLocal_ShouldThrowException_WhenFileDoesNotExist() {
        long studentId = 1L;
        String filePath = "invalid/path/to/avatar.png";
        Avatar mockAvatar = new Avatar();
        mockAvatar.setFilePath(filePath);

        assertThrows(MissingStudentException.class, () -> out.getAvatarFromLocal(studentId));
    }

    @Test
    void testGetAllAvatars() {
        Avatar avatar1 = new Avatar();
        Avatar avatar2 = new Avatar();
        List<Avatar> avatarList = Arrays.asList(avatar1, avatar2);

        int pageNumber = 2;
        int pageSize = 2;
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<Avatar> page = new PageImpl<>(avatarList, pageRequest, avatarList.size());

        when(avatarRepository.findAll(pageRequest)).thenReturn(page);

        List<Avatar> result = out.getAllAvatars(pageNumber, pageSize);
        assertEquals(2, result.size());
        assertEquals(avatarList, result);
    }

}