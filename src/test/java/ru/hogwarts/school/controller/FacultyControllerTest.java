package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {

    @MockBean
    private FacultyServiceImpl facultyService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void createFaculty() throws Exception {
        Faculty faculty = new Faculty("testName", "testColor");
        faculty.setId(1L);

        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(faculty);

        mvc.perform(post("/faculty/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testName\", \"color\": \"testColor\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.color").value("testColor"));
    }

    @Test
    public void getFaculty() throws Exception {
        Faculty faculty = new Faculty("testName", "testColor");
        faculty.setId(1L);

        when(facultyService.getFacultyById(1L)).thenReturn(faculty);

        mvc.perform(get("/faculty/1/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.color").value("testColor"));
    }

    @Test
    public void getFaculty_WhenFacultyNotFound() throws Exception {
        when(facultyService.getFacultyById(1L)).thenReturn(null);

        mvc.perform(get("/faculty/1/get"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFaculty() throws Exception {
        Faculty updatedFaculty = new Faculty("testName", "testColor");
        updatedFaculty.setId(1L);

        Faculty existingFaculty = new Faculty("testName", "testColor");
        existingFaculty.setId(1L);

        when(facultyService.updateFaculty(eq(1L), any(Faculty.class))).thenReturn(updatedFaculty);

        mvc.perform(put("/faculty/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testName\", \"color\": \"testColor\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.color").value("testColor"));
    }

    @Test
    public void updateFaculty_WhenFacultyNotFound() throws Exception {
        when(facultyService.updateFaculty(eq(1L), any(Faculty.class))).thenReturn(null);

        mvc.perform(put("/faculty/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testName\", \"color\": \"testColor\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteFaculty() throws Exception {
        mvc.perform(delete("/faculty/1/remove"))
                .andExpect(status().isOk());
    }

    @Test
    public void filterFacultiesByColor() throws Exception {
        Faculty faculty = new Faculty("testName", "testColor");
        faculty.setId(1L);

        when(facultyService.filterFacultiesByColor("testName")).thenReturn(List.of(faculty));

        mvc.perform(get("/faculty/colors").param("color", "testName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("testName"))
                .andExpect(jsonPath("$[0].color").value("testColor"));
    }

    @Test
    public void filterFacultiesByColor_WhenFacultyCollectionIsEmpty() throws Exception {
        when(facultyService.filterFacultiesByColor("testColor")).thenReturn(Collections.emptyList());

        mvc.perform(get("/faculty/colors").param("color", "testName"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void getFacultyByColorIgnoreCaseOrNameIgnoreCase() throws Exception {
        Faculty faculty = new Faculty("testName", "testColor");
        faculty.setId(1L);

        when(facultyService.getFacultyByColorIgnoreCaseOrNameIgnoreCase("testColor", "testName")).thenReturn(List.of(faculty));

        mvc.perform(get("/faculty/colors-or-name")
                        .param("color", "testColor")
                        .param("name", "testName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("testName"))
                .andExpect(jsonPath("$[0].color").value("testColor"));
    }

    @Test
    public void findFacultyByStudentId() throws Exception {
        Student student = new Student("testName", 22);
        student.setId(1L);

        when(facultyService.findByFacultyId(1L)).thenReturn(List.of(student));

        mvc.perform(get("/faculty/1/get/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("testName"))
                .andExpect(jsonPath("$[0].age").value(22));
    }

}