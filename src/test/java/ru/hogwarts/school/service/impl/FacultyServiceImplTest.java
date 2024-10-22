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
    void getFacultyByIdThrowsMissingFacultyException(){
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
    @DisplayName("Тест выброса исключения при попытке обновления несуществуюзего факультета")
    void updateFacultyMissingFacultyException(){
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
        Faculty deletedFaculty = out.getFacultyById(id);
        assertNull(deletedFaculty);
    }

    @Test
    @DisplayName("Тест метода фильтрации факультетов по цвету")
    void filterFacultiesByColor() {
        //setup
        String color = "Red";

        Faculty faculty = new Faculty("testName", "Yellow");
        Faculty expected = new Faculty("testName", color);
        Faculty expected2 = new Faculty("testName", color);
        Faculty testFaculty = out.createFaculty(faculty);
        Faculty testFaculty1 = out.createFaculty(expected);
        Faculty testFaculty2 = out.createFaculty(expected2);

        //test
        List<Faculty> actual = out.filterFacultiesByColor(color);

        //check
        assertTrue(actual.containsAll(List.of(expected, expected2)));
    }
}