package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudentById(Long studentId);

    Student updateStudent(Long studentId, Student student);

    Student deleteStudent(Long studentId);

    List<Student> ageStudentFilter(int age);
}
