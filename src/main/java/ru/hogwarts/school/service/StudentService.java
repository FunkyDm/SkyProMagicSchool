package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudentById(long id);

    Student updateStudent(long id, Student student);

    Student deleteStudent(long id);

    List<Student> filterStudentsByAge(int age);
}
