package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {
    private final FacultyServiceImpl out = new FacultyServiceImpl();

    @Test
    @DisplayName("Тест метода создания факультета")
    void createFaculty() {
        //setup
        Faculty expected = new Faculty("testName", "testColor");

        //test
        Faculty actual = out.createFaculty(expected);

        //check
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест метода получения факультета по id")
    void getFacultyById() {
        //setup
        Faculty expected = new Faculty("testName", "testColor");
        Faculty faculty = out.createFaculty(expected);

        //test
        Faculty actual = out.getFacultyById(faculty.getId());

        //check
        assertEquals(actual, faculty);
    }

    @Test
    @DisplayName("Тест метода обновления факультета")
    void updateFaculty() {
        //setup
        Faculty expected = new Faculty("testName", "testColor");
        Faculty faculty1 = out.createFaculty(expected);

        //test
        out.updateFaculty(faculty1.getId(), expected);

        //check
        Faculty actual = out.getFacultyById(faculty1.getId());
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Тест метода удаления факультета")
    void deleteFaculty() {
        //setup
        Faculty expected = new Faculty("testName", "testColor");
        Faculty faculty = out.createFaculty(expected);

        //test
        Faculty actual = out.deleteFaculty(faculty.getId());

        //check
        assertEquals(actual, faculty);
        Faculty faculty1 = out.getFacultyById(faculty.getId());
        assertNull(faculty1);
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