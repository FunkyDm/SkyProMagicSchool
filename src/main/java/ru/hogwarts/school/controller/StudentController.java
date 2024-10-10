package ru.hogwarts.school.controller;

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
    public Student createStudent(@RequestBody Student student) {
        return studentServiceImpl.createStudent(student);
    }

    @GetMapping("/{studentId}/get")
    public Student getStudent(@PathVariable("studentId") Long studentId) {
        return studentServiceImpl.getStudentById(studentId);
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student student) {
        return studentServiceImpl.updateStudent(student.getId(), student);
    }

    @DeleteMapping("/{studentId}/remove")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentServiceImpl.deleteStudent(studentId);
    }

    @GetMapping("/age")
    public List<Student> filterStudentsByAge(@RequestParam int studentAge) {
        return studentServiceImpl.filterStudentsByAge(studentAge);
    }

}
