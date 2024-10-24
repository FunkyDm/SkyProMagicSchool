package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.MissingStudentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl out;

    @Test
    @DisplayName("Тест метода создания студента")
    void createStudent() {
        //setup
        Student expected = new Student("testName", 20);

        when(studentRepository.save(expected)).thenReturn(expected);

        //test
        Student actual = out.createStudent(expected);

        //check
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Тест метода получения студента по id")
    void getStudentById() {
        //setup
        long id = 1L;
        Student expected = new Student("testName", 20);
        expected.setId(id);

        when(studentRepository.findById(id)).thenReturn(Optional.of(expected));

        //test
        Student actual = out.getStudentById(id);

        //check
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Тест выброса исключения при попытке найти несуществующего студента")
    void getStudentByIdThrowsMissingStudentException() {
        //setup
        long id = 1L;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        //check
        assertThrows(MissingStudentException.class, () -> out.getStudentById(id));
    }

    @Test
    @DisplayName("Тест метода обновления студента")
    void updateStudent() {
        //setup
        long id = 1L;
        Student expected = new Student("testName", 20);
        expected.setId(id);

        when(studentRepository.existsById(id)).thenReturn(true);
        when(studentRepository.save(expected)).thenReturn(expected);

        //test
        Student actual = out.updateStudent(id, expected);

        //check
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Тест выброса исключения при попытке обновления несуществующего студента")
    void updateStudentMissingStudentException() {
        //setup
        long id = 1L;
        Student expected = new Student("testName", 20);
        expected.setId(id);

        //check
        assertThrows(MissingStudentException.class, () -> out.updateStudent(id, expected));
    }

    @Test
    @DisplayName("Тест метода удаления студента")
    void deleteStudent() {
        //setup
        long id = 1L;
        Student expected = new Student("testName", 20);
        expected.setId(id);

        when(studentRepository.findById(id)).thenReturn(Optional.of(expected));

        //test
        Student actual = out.deleteStudent(id);

        //check
        assertEquals(actual, expected);

        when(studentRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Student> deletedStudent = studentRepository.findById(id);
        assertTrue(deletedStudent.isEmpty());
    }

    @Test
    @DisplayName("Тест выброса исключения при попытке удаления несуществующего студента")
    void deleteStudentMissingStudentException() {
        //setup
        long id = 1L;
        Student expected = new Student("testName", 20);
        expected.setId(id);

        when(studentRepository.findById(id)).thenReturn(Optional.of(expected));

        //test
        Student actual = out.deleteStudent(id);

        //check
        assertEquals(actual, expected);

        when(studentRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Student> deletedStudent = studentRepository.findById(id);
        assertTrue(deletedStudent.isEmpty());
    }

    @Test
    @DisplayName("Тест метода фильтрации студентов по возрасту")
    void filterStudentsByAge() {
        //setup
        Student student1 = new Student("testName1", 20);
        Student student2 = new Student("testName2", 18);
        Student student3 = new Student("testName3", 20);

        List<Student> allStudents = Arrays.asList(student1, student2, student3);
        when(studentRepository.findAll()).thenReturn(allStudents);

        //test
        List<Student> result = out.filterStudentsByAge(20);

        //check
        assertTrue(result.contains(student1));
        assertTrue(result.contains(student3));
    }
}