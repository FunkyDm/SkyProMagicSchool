package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {

    @SpyBean
    private FacultyServiceImpl facultyService;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

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

        when(facultyRepository.findById(1l)).thenReturn(Optional.of(faculty));
        when(facultyService.getFacultyById(1L)).thenReturn(faculty);

        mvc.perform(get("/faculty/1/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.color").value("testColor"));
    }

    @Test
    public void getFaculty_WhenFacultyNotFound() throws Exception {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(new Faculty("testName", "testColor")));
        when(facultyService.getFacultyById(1L)).thenReturn(null);

        mvc.perform(get("/faculty/1/get"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateFaculty() throws Exception{
        Faculty updatedFaculty = new Faculty("testName" , "testColor");
        updatedFaculty.setId(1L);

        when(facultyRepository.existsById(1L)).thenReturn(true);
        when(facultyRepository.save(updatedFaculty)).thenReturn(updatedFaculty);
        when(facultyService.updateFaculty(eq(1L), any(Faculty.class))).thenReturn(updatedFaculty);

        mvc.perform(put("/faculty/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"testName\", \"color\": \"testColor\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.color").value("testColor"));
    }

}