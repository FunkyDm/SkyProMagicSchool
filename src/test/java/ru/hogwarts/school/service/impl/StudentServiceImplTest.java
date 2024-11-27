package ru.hogwarts.school.service.impl;

import org.checkerframework.checker.units.qual.A;
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
    private StudentServiceImpl studentService;

    @Test
    @DisplayName("Тест метода создания студента")
    void createStudent() {
        //setup
        Student expected = new Student("testName", 20);

        when(studentRepository.save(expected)).thenReturn(expected);

        //test
        Student actual = studentService.createStudent(expected);

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
        Student actual = studentService.getStudentById(id);

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
        assertThrows(MissingStudentException.class, () -> studentService.getStudentById(id));
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
        Student actual = studentService.updateStudent(id, expected);

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
        assertThrows(MissingStudentException.class, () -> studentService.updateStudent(id, expected));
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
        Student actual = studentService.deleteStudent(id);

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
        Student actual = studentService.deleteStudent(id);

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
        List<Student> result = studentService.filterStudentsByAge(20);

        //check
        assertTrue(result.contains(student1));
        assertTrue(result.contains(student3));
    }

    @Test
    void testGetAllStudentsAmount() {
        when(studentRepository.getAllStudentsAmount()).thenReturn(100);

        Integer result = studentService.getAllStudentsAmount();
        assertEquals(100, result);
    }

    @Test
    void testGetAvgStudentAge() {
        when(studentRepository.getAvgStudentAge()).thenReturn(18.75);

        Double result = studentService.getAvgStudentAge();
        assertEquals(18.75, result);
    }

    @Test
    void testGetLastFiveStudents() {
        Student student1 = new Student("test1", 20);
        Student student2 = new Student("test2", 20);
        Student student3 = new Student("test3", 20);
        Student student4 = new Student("test4", 20);
        Student student5 = new Student("test5", 20);
        List<Student> lastFiveStudents = Arrays.asList(student1, student2, student3, student4, student5);

        when(studentRepository.getLastFiveStudents()).thenReturn(lastFiveStudents);

        List<Student> result = studentService.getLastFiveStudents();
        assertEquals(5, result.size());
        assertEquals(lastFiveStudents, result);
    }

    @Test
    void testFindAllNameStartsWithA() {
        Student expectedStudent1 = new Student("aTest1", 20);
        Student expectedStudent2 = new Student("aTest2", 20);
        Student notExpectedStudent = new Student("test3", 20);

        List<Student> allStudents = Arrays.asList(expectedStudent1, expectedStudent2, notExpectedStudent);

        when(studentRepository.findAll()).thenReturn(allStudents);

        List<Student> result = studentService.findAllNameStartsWithA();

        assertTrue(result.contains(expectedStudent1) && result.contains(expectedStudent2)
                && !result.contains(notExpectedStudent));
    }

    @Test
    void testGetAllAvgStudentAgeStream(){
        Student student1 = new Student("test1", 20);
        Student student2 = new Student("test2", 20);

        List<Student> allStudents = Arrays.asList(student1,student2);

        when(studentRepository.findAll()).thenReturn(allStudents);

        double result = studentService.getAllAvgStudentAgeStream();

        assertEquals(result, 20.0);
    }

}