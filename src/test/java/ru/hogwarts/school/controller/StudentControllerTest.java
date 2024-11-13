package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentControllerTest {

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
        studentRepository.save(new Student("testName", 22));
    }

    @Test
    void createStudent() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"name\": \"testName\", \"age\": \"22\"}", headers);

        ResponseEntity<Student> response = restTemplate.postForEntity("/student/add", request, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("testName");
        assertThat(response.getBody().getAge()).isEqualTo(22);

        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(2);
    }

    @Test
    public void getStudent(){
        Student student = studentRepository.save(new Student("testName", 22));

        ResponseEntity<Student> response = restTemplate.getForEntity("/student/" + student.getId() + "/get", Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(student.getId());
        assertThat(response.getBody().getName()).isEqualTo("testName");
        assertThat(response.getBody().getAge()).isEqualTo(22);
    }

    @Test
    public void updateStudent(){
        Student existingStudent = studentRepository.save(new Student("testName", 22));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"name\": \"testNameUpdated\", \"age\": \"22\"}", headers);

        ResponseEntity<Student> response = restTemplate.exchange("/student/" + existingStudent.getId() + "/update", HttpMethod.PUT, request, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("testNameUpdated");

        Student updatedStudent = studentRepository.findById(existingStudent.getId()).orElse(null);
        assertThat(updatedStudent).isNotNull();
        assertThat(updatedStudent.getName()).isEqualTo("testNameUpdated");
    }

    @Test
    public void deleteStudent(){
        Student deletedStudent = studentRepository.save(new Student("testName", 22));

        ResponseEntity<Void> response = restTemplate.exchange("/student/" + deletedStudent.getId() + "/remove", HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void filterStudentsByAge(){
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/student/age?age=22", Student[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody()[0].getName()).isEqualTo("testName");
    }

    @Test
    public void findByAgeBetween(){
        ResponseEntity<Student[]> response = restTemplate.getForEntity("/student/age-between?min=19&max=22", Student[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody()[0].getName()).isEqualTo("testName");
    }

    @Test
    public void getFacultyByStudentId(){
        Student student = new Student("testName", 22);
        Faculty faculty = facultyRepository.save(new Faculty("testName", "testColor"));
        student.setFaculty(faculty);
        studentRepository.save(student);

        ResponseEntity<Faculty> response = restTemplate.getForEntity("/student/" + student.getId() + "/get/faculty", Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("testName");
    }

}