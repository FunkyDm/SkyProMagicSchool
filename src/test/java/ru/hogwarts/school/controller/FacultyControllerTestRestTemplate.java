package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FacultyControllerTestRestTemplate {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @BeforeEach
    public void setup(){
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
        facultyRepository.save(new Faculty("testName", "testColor"));
    }

    @Test
    public void createFaculty() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"name\": \"testName\", \"color\": \"testColor\"}", headers);

        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty/add", request, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("testName");
        assertThat(response.getBody().getColor()).isEqualTo("testColor");

        List<Faculty> faculties = facultyRepository.findAll();
        assertThat(faculties).hasSize(2);
    }

    @Test
    public void getFaculty(){
        Faculty faculty = facultyRepository.save(new Faculty("testName", "testColor"));

        ResponseEntity<Faculty> response = restTemplate.getForEntity("/faculty/" + faculty.getId() + "/get", Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(faculty.getId());
        assertThat(response.getBody().getName()).isEqualTo("testName");
        assertThat(response.getBody().getColor()).isEqualTo("testColor");
    }

    @Test
    public void updateFaculty(){
        Faculty existingFaculty = facultyRepository.save(new Faculty("testName", "testColor"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"name\": \"testNameUpdated\", \"color\": \"testColor\"}", headers);

        ResponseEntity<Faculty> response = restTemplate.exchange("/faculty/" + existingFaculty.getId() + "/update", HttpMethod.PUT, request, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("testNameUpdated");

       Faculty updatedFaculty = facultyRepository.findById(existingFaculty.getId()).orElse(null);
       assertThat(updatedFaculty).isNotNull();
       assertThat(updatedFaculty.getName()).isEqualTo("testNameUpdated");
    }

    @Test
    public void deleteFaculty(){
        Faculty deletedFaculty = facultyRepository.save(new Faculty("testName", "testColor"));

        ResponseEntity<Void> response = restTemplate.exchange("/faculty/" + deletedFaculty.getId() + "/remove", HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void filterFacultiesByColor(){
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("/faculty/colors?color=testColor&name=testColor", Faculty[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody()[0].getName()).isEqualTo("testName");
    }

    @Test
    public void getFacultyByColorIgnoreCaseOrNameIgnoreCase(){
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity("/faculty/colors-or-name?color=testColor&name=testName", Faculty[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody()[0].getName()).isEqualTo("testName");
    }

    @Test
    public void findFacultyByStudentId(){
        Faculty faculty = facultyRepository.save(new Faculty("testName", "testColor"));
        Student student = new Student("testName", 22);
        student.setFaculty(faculty);
        studentRepository.save(student);

        ResponseEntity<Student[]> response = restTemplate.getForEntity("/faculty/" + faculty.getId() + "/get/students", Student[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()[0].getName()).isEqualTo("testName");
    }

}