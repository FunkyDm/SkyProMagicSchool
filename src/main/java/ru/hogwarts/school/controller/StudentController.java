package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;

    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student) {
        return studentServiceImpl.createStudent(student);
    }

    @GetMapping("/{studentId}/get")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Student getStudent(@PathVariable("studentId") Long studentId) {
        return studentServiceImpl.getStudentById(studentId);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Student updateStudent(@PathVariable("id") Long id,@RequestBody Student student) {
        return studentServiceImpl.updateStudent(id, student);
    }

    @DeleteMapping("/{studentId}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentServiceImpl.deleteStudent(studentId);
    }

    @GetMapping("/age")
    public List<Student> filterStudentsByAge(@RequestParam int studentAge) {
        return studentServiceImpl.filterStudentsByAge(studentAge);
    }

}
