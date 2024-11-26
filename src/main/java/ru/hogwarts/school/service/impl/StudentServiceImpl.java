package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.MissingStudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements ru.hogwarts.school.service.StudentService {
    private final StudentRepository studentRepository;

    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
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
    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyByStudentId(long id) {
        return facultyRepository.findByStudentsId(id);
    }

    @Override
    public int getAllStudentsAmount() {
        return studentRepository.getAllStudentsAmount();
    }

    @Override
    public double getAvgStudentAge() {
        return studentRepository.getAvgStudentAge();
    }

    @Override
    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public void printParallel() {
        List<Student> students = new ArrayList<>(studentRepository.findAll());
        printInStream(students, 0, 2);
        List<Thread> threadList = List.of(
                new Thread(() -> printInStream(students, 2, 2)),
                new Thread(() -> printInStream(students, 4, 2)));
        for(Thread thread : threadList){
            thread.start();
        }
    }

    @Override
    public void printParallelSynchronized() {
        List<Student> students = new ArrayList<>(studentRepository.findAll());
        printInStreamSynchronized(students, 0, 2);
        List<Thread> threadList = List.of(
                new Thread(() -> printInStreamSynchronized(students, 2, 2)),
                new Thread(() -> printInStreamSynchronized(students, 4, 2)));
        for(Thread thread : threadList){
            thread.start();
        }
    }

    private void printInStreamSynchronized(Collection<Student> students, int offSet, int limit){
        synchronized (this){
            printInStream(students, offSet, limit);
        }
    }

    private void printInStream(Collection<Student> students, int offSet, int limit) {
        System.out.printf("Current thread: %s%n", Thread.currentThread().getName());
        students.stream()
                .skip(offSet)
                .limit(limit)
                .forEach(System.out::println);
    }

}
