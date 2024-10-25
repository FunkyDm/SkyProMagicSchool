package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.MissingFacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFacultyById(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new MissingFacultyException(id));
    }

    @Override
    public Faculty updateFaculty(long id, Faculty facultyForUpdate) {
        if (!facultyRepository.existsById(id)) {
            throw new MissingFacultyException(id);
        }
        facultyForUpdate.setId(id);
        return facultyRepository.save(facultyForUpdate);
    }

    @Override
    public Faculty deleteFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new MissingFacultyException(id));
        facultyRepository.delete(faculty);
        return faculty;
    }

    @Override
    public List<Faculty> filterFacultiesByColor(String color) {
        return facultyRepository.findAll().stream()
                .filter(f -> (Objects.equals(f.getColor(), color)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Faculty> getFacultyByColorIgnoreCaseOrNameIgnoreCase(String color, String name){
        return facultyRepository.getFacultyByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

}
