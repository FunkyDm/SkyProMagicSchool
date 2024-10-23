package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.MissingFacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyServiceImpl out;

    @Test
    @DisplayName("Тест метода добавления факультета")
    void createFaculty() {
        //setup
        Faculty expected = new Faculty("testName", "testColor");

        when(facultyRepository.save(expected)).thenReturn(expected);

        //test
        Faculty actual = out.createFaculty(expected);

        //check
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест метода получения факультета по id")
    void getFacultyById() {
        //setup
        long id = 1L;
        Faculty faculty = new Faculty("testName", "testColor");
        faculty.setId(id);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));

        //test
        Faculty actual = out.getFacultyById(id);

        //check
        assertEquals(actual, faculty);
    }

    @Test
    @DisplayName("Тест выброса исключения при попытке найти несуществующий факультет")
    void getFacultyByIdThrowsMissingFacultyException() {
        //setup
        long id = 1L;
        when(facultyRepository.findById(id)).thenReturn(Optional.empty());

        //check
        assertThrows(MissingFacultyException.class, () -> out.getFacultyById(id));
    }

    @Test
    @DisplayName("Тест метода обновления факультета")
    void updateFaculty() {
        //setup
        long id = 1L;
        Faculty expected = new Faculty("testName", "testColor");
        expected.setId(id);

        when(facultyRepository.existsById(id)).thenReturn(true);
        when(facultyRepository.save(expected)).thenReturn(expected);

        //test
        Faculty actual = out.updateFaculty(id, expected);

        //check
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Тест выброса исключения при попытке обновления несуществующего факультета")
    void updateFacultyMissingFacultyException() {
        //setup
        long id = 1L;
        Faculty expected = new Faculty("testName", "testColor");
        expected.setId(id);

        //check
        assertThrows(MissingFacultyException.class, () -> out.updateFaculty(id, expected));
    }

    @Test
    @DisplayName("Тест метода удаления факультета")
    void deleteFaculty() {
        //setup
        long id = 1L;
        Faculty expected = new Faculty("testName", "testColor");
        expected.setId(id);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(expected));

        //test
        Faculty actual = out.deleteFaculty(id);

        //check
        assertEquals(actual, expected);

        when(facultyRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Faculty> deletedFaculty = facultyRepository.findById(id);
        assertTrue(deletedFaculty.isEmpty());
    }

    @Test
    @DisplayName("Тест выброса исключения при попытке удаления несуществующего факультета")
    void deleteFacultyMissingFacultyException() {
        //setup
        long id = 1L;
        Faculty expected = new Faculty("testName", "testColor");
        expected.setId(id);

        when(facultyRepository.findById(id)).thenReturn(Optional.of(expected));

        //test
        Faculty actual = out.deleteFaculty(id);

        //check
        assertEquals(actual, expected);

        when(facultyRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Faculty> deletedFaculty = facultyRepository.findById(id);
        assertTrue(deletedFaculty.isEmpty());
    }

    @Test
    @DisplayName("Тест метода фильтрации факультетов по цвету")
    void filterFacultiesByColor() {
        //setup
        Faculty faculty1 = new Faculty("testName1", "Red");
        Faculty faculty2 = new Faculty("testName2", "Blue");
        Faculty faculty3 = new Faculty("testName3", "Red");

        List<Faculty> allFaculties = Arrays.asList(faculty1, faculty2, faculty3);
        when(facultyRepository.findAll()).thenReturn(allFaculties);

        //test
        List<Faculty> result = out.filterFacultiesByColor("Red");

        //check
        assertTrue(result.contains(faculty1));
        assertTrue(result.contains(faculty3));
    }
}