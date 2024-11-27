package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.MissingFacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements ru.hogwarts.school.service.FacultyService {
    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFacultyById(long id) {
        logger.info("Was invoked method for getting faculty");
        return facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("There is not faculty with id = {}", id);
            return new MissingFacultyException(id);
        });
    }

    @Override
    public Faculty updateFaculty(long id, Faculty facultyForUpdate) {
        if (!facultyRepository.existsById(id)) {
            logger.error("There is not faculty with id = {}", id);
            throw new MissingFacultyException(id);
        }
        facultyForUpdate.setId(id);
        logger.info("Was invoked method for updating faculty with id = {}", id);
        return facultyRepository.save(facultyForUpdate);
    }

    @Override
    public Faculty deleteFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("There is not faculty with id = {}", id);
            return new MissingFacultyException(id);
        });
        facultyRepository.delete(faculty);
        logger.info("Was invoked method for deleting faculty");
        return faculty;
    }

    @Override
    public List<Faculty> filterFacultiesByColor(String color) {
        logger.info("Was invoked method for filter faculties by color");
        return facultyRepository.findAll().stream()
                .filter(f -> (Objects.equals(f.getColor(), color)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Faculty> getFacultyByColorIgnoreCaseOrNameIgnoreCase(String color, String name){
        logger.info("Was invoked method for filter faculties by name or color ignore case");
        return facultyRepository.getFacultyByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    @Override
    public List<Student> findByFacultyId(long id){
        logger.info("Was invoked method for finding faculty with id = {}", id);
        return studentRepository.findByFacultyId(id);
    }

}
