package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.MissingStudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements ru.hogwarts.school.service.StudentService {
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(long id) {
        logger.info("Was invoked method for getting student with id = {}", id);
        return studentRepository.findById(id).orElseThrow(() -> {
            logger.error("There is not student with id = {}", id);
            return new MissingStudentException(id);
        });
    }

    @Override
    public Student updateStudent(long id, Student studentForUpdate) {
        if (!studentRepository.existsById(id)) {
            logger.error("There is not student with id = {}", id);
            throw new MissingStudentException(id);
        }
        studentForUpdate.setId(id);
        logger.info("Was invoked method for updating student with id = {}", id);
        return studentRepository.save(studentForUpdate);
    }

    @Override
    public Student deleteStudent(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> {
            logger.error("There is not student with id = {}", id);
            return new MissingStudentException(id);
        });
        logger.info("Was invoked method for deleting student");
        studentRepository.delete(student);
        return student;
    }

    @Override
    public List<Student> filterStudentsByAge(int age) {
        logger.info("Was invoked method for filtering students by age");
        return studentRepository.findAll().stream()
                .filter(s -> (s.getAge() == age))
                .collect(Collectors.toList());
    }

    @Override
    public List<Student> findByAgeBetween(int min, int max){
        logger.info("Was invoked method for finding students between ages");
        return studentRepository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyByStudentId(long id) {
        logger.info("Was invoked method for finding faculty of student by id of student");
        return facultyRepository.findByStudentsId(id);
    }

    @Override
    public int getAllStudentsAmount(){
        logger.info("Was invoked method for getting amount of all students");
        return studentRepository.getAllStudentsAmount();
    }

    @Override
    public double getAvgStudentAge(){
        logger.info("Was invoked method for getting average age of all students");
        return studentRepository.getAvgStudentAge();
    }

    @Override
    public List<Student> getLastFiveStudents(){
        logger.info("Was invoked method for getting last five students");
        return studentRepository.getLastFiveStudents();
    }

    @Override
    public List<Student> findAllNameStartsWithA(){
        return studentRepository.findAll().stream()
                .filter(s -> s.getName().toUpperCase().startsWith("A"))
                .toList();
    }

    @Override
    public double getAllAvgStudentAgeStream(){
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0);
    }

}
