package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @MockBean
    private StudentServiceImpl studentService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void createStudent() throws Exception {
        Student student = new Student("testName", 22);
        student.setId(1L);

        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mvc.perform(post("/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testName\", \"age\": \"22\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.age").value(22));
    }

    @Test
    public void getStudent() throws Exception {
        Student student = new Student("testName", 22);
        student.setId(1L);

        when(studentService.getStudentById(1L)).thenReturn(student);

        mvc.perform(get("/student/1/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.age").value(22));
    }

    @Test
    public void getStudent_WhenStudentNotFound() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(null);

        mvc.perform(get("/student/1/get"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateStudent() throws Exception {
        Student updatedStudent = new Student("testName", 22);
        updatedStudent.setId(1L);

        Student existingStudent = new Student("testName", 22);
        existingStudent.setId(1L);

        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(updatedStudent);

        mvc.perform(put("/student/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testName\", \"age\": \"22\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.age").value(22));
    }

    @Test
    public void updateStudent_WhenStudentNotFound() throws Exception {
        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(null);

        mvc.perform(put("/student/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testName\", \"age\": \"22\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteStudent() throws Exception {
        mvc.perform(delete("/student/1/remove")).andExpect(status().isOk());
    }

    @Test
    public void filterStudentByAge() throws Exception {
        Student student = new Student("testName", 22);
        student.setId(1L);

        when(studentService.filterStudentsByAge(22)).thenReturn(List.of(student));

        mvc.perform(get("/student/age").param("age", "22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("testName"))
                .andExpect(jsonPath("$[0].age").value(22));
    }

    @Test
    public void filterStudentByAge_WhenStudentCollectionIsEmpty() throws Exception{
        when(studentService.filterStudentsByAge(22)).thenReturn(Collections.emptyList());

        mvc.perform(get("/student/age").param("age", "22"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void findByAgeBetween() throws Exception{
        Student student = new Student("testName", 20);
        student.setId(1L);

        when(studentService.findByAgeBetween(19,21)).thenReturn(List.of(student));

        mvc.perform(get("/student/age-between")
                        .param("min", "19")
                        .param("max", "21"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("testName"))
                .andExpect(jsonPath("$[0].age").value(20));
    }

    @Test
    public void getFacultyByStudentId() throws Exception{
        Student student = new Student("testName", 22);
        student.setId(1L);

        Faculty faculty = new Faculty("testName", "testColor");
        faculty.setId(1L);

        when(studentService.getFacultyByStudentId(student.getId())).thenReturn(faculty);

        mvc.perform(get("/student/1/get/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.color").value("testColor"));
    }

}