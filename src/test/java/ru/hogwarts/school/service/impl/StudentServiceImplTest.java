//package ru.hogwarts.school.service.impl;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import ru.hogwarts.school.model.Faculty;
//import ru.hogwarts.school.model.Student;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class StudentServiceImplTest {
//    private final StudentServiceImpl out = new StudentServiceImpl();
//
//    @Test
//    @DisplayName("Тест метода создания студента")
//    void createStudent() {
//        //setup
//        Student expected = new Student("testName", 20);
//
//        //test
//        Student actual = out.createStudent(expected);
//
//        //check
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @DisplayName("Тест метода получения студента по id")
//    void getStudentById() {
//        //setup
//        Student expected = new Student("testName", 20);
//        Student student = out.createStudent(expected);
//
//        //test
//        Student actual = out.getStudentById(student.getId());
//
//        //check
//        assertEquals(actual, student);
//    }
//
//    @Test
//    @DisplayName("Тест метода обновления студента")
//    void updateStudent() {
//        //setup
//        Student expected = new Student("testName", 20);
//        Student student1 = out.createStudent(expected);
//
//        //test
//        out.updateStudent(student1.getId(), expected);
//
//        //check
//        Student actual = out.getStudentById(student1.getId());
//        assertEquals(actual, expected);
//    }
//
//    @Test
//    @DisplayName("Тест метода удаления студента")
//    void deleteStudent() {
//        //setup
//        Student expected = new Student("testName", 20);
//        Student student = out.createStudent(expected);
//
//        //test
//        Student actual = out.deleteStudent(student.getId());
//
//        //check
//        assertEquals(actual, student);
//        Student student1 = out.getStudentById(student.getId());
//        assertNull(student1);
//    }
//
//    @Test
//    @DisplayName("Тест метода фильтрации студентов по возрасту")
//    void filterStudentsByAge() {
//        //setup
//        int age = 20;
//
//        Student student = new Student("testName", 19);
//        Student expected = new Student("testName", age);
//        Student expected2 = new Student("testName", age);
//        Student testStudent = out.createStudent(student);
//        Student testStudent1 = out.createStudent(expected);
//        Student testStudent2 = out.createStudent(expected2);
//
//        //test
//        List<Student> actual = out.filterStudentsByAge(age);
//
//        //check
//        assertTrue(actual.containsAll(List.of(expected, expected2)));
//    }
//}