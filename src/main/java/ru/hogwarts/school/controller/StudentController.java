package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Collections;
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

    @GetMapping("/{id}/get")
    //@ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Student> getStudent(@PathVariable("id") long id) {
        Student student = studentServiceImpl.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}/update")
    //@ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id,
                                                 @RequestBody Student student) {
        Student foundStudent = studentServiceImpl.updateStudent(id, student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}/remove")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") long id) {
        studentServiceImpl.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age")
    public ResponseEntity<List<Student>> filterStudentsByAge(@RequestParam int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentServiceImpl.filterStudentsByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/age-between")
    public List<Student> findByAgeBetween(@RequestParam(value = "min") int min,
                                          @RequestParam(value = "max") int max) {
        return studentServiceImpl.findByAgeBetween(min, max);
    }

}
