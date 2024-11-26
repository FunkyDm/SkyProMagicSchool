package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<Student> getStudent(@PathVariable("id") long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id,
                                                 @RequestBody Student student) {
        Student foundStudent = studentService.updateStudent(id, student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age")
    public ResponseEntity<List<Student>> filterStudentsByAge(@RequestParam int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.filterStudentsByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/age-between")
    public List<Student> findByAgeBetween(@RequestParam(value = "min") int min,
                                          @RequestParam(value = "max") int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @GetMapping("/{id}/get/faculty")
    public Faculty getFacultyByStudentId(@PathVariable("id") long id) {
        return studentService.getFacultyByStudentId(id);
    }

    @GetMapping("/get/all-students-amount")
    public Integer getAllStudentsAmount() {
        return studentService.getAllStudentsAmount();
    }

    @GetMapping("/get/avg-student-age")
    public Double getAvgStudentAge() {
        return studentService.getAvgStudentAge();
    }

    @GetMapping("/get/five-last-students")
    public List<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/get/print-parallel")
    public void printParallel(){
        studentService.printParallel();
    }

    @GetMapping("/get/print-synchronized")
    void printParallelSynchronized(){
        studentService.printParallelSynchronized();
    }

}
