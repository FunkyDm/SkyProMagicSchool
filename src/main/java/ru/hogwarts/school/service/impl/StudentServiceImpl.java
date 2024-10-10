package ru.hogwarts.school.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> studentRepository = new HashMap<>();

    private static Long studentCounter = 1L;

    @PostConstruct
    public void init(){
        createStudent(new Student("Pavel", 40));
        createStudent(new Student("Alexander", 35));
        createStudent(new Student("Kirill", 49));
        createStudent(new Student("Oleg", 40));
        createStudent(new Student("Lexa", 35));
    }

    @Override
    public Student createStudent(Student student) {
        student.setId(studentCounter++);
        studentRepository.put(student.getId(), student);
        return student;
    }

    @Override
    public Student getStudentById(Long studentId) {
        return studentRepository.get(studentId);
    }

    @Override
    public Student updateStudent(Long studentId, Student student) {
        student.setId(studentId);
        studentRepository.put(studentId, student);
        return student;
    }

    @Override
    public Student deleteStudent(Long studentId) {
        return studentRepository.remove(studentId);
    }

    @Override
    public List<Student> filterStudentsByAge(int age) {
        return studentRepository.values().stream()
                .filter(s -> (s.getAge() == age))
                .collect(Collectors.toList());
    }

}
