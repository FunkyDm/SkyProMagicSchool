package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.MissingStudentException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new MissingStudentException(id));
    }

    @Override
    public Student updateStudent(long id, Student studentForUpdate) {
        if (!studentRepository.existsById(id)) {
            throw new MissingStudentException(id);
        }
        studentForUpdate.setId(id);
        return studentRepository.save(studentForUpdate);
    }

    @Override
    public Student deleteStudent(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new MissingStudentException(id));
        studentRepository.delete(student);
        return student;
    }

    @Override
    public List<Student> filterStudentsByAge(int age) {
        return studentRepository.findAll().stream()
                .filter(s -> (s.getAge() == age))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findByAgeBetween(int min, int max){
        return studentRepository.findByAgeBetween(min, max);
    }

}
