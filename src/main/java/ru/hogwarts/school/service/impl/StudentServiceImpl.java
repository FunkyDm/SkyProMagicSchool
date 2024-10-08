package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    Map<Long, Student> students = new HashMap<>();

    private static Long studentCounter = 1L;

    @Override
    public Student createStudent(Student student) {
        students.put(studentCounter, student);
        studentCounter++;
        return student;
    }

    @Override
    public Student getStudentById(Long studentId) {
        return students.get(studentId);
    }

    @Override
    public Student updateStudent(Long studentId, Student student) {
        students.put(studentId, student);
        return student;
    }

    @Override
    public Student deleteStudent(Long studentId) {
        return students.remove(studentId);
    }

    @Override
    public List<Student> ageStudentFilter(int age){
        return students.values().stream()
                .filter(s -> (s.getAge() == age))
                .collect(Collectors.toList());
    }

}
