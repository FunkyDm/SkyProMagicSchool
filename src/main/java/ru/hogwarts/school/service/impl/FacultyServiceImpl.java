package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    Map<Long, Faculty> faculties = new HashMap<>();

    private static Long facultyCounter = 1L;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculties.put(facultyCounter, faculty);
        facultyCounter++;
        return faculty;
    }

    @Override
    public Faculty getFacultyById(Long facultyId) {
        return faculties.get(facultyId);
    }

    @Override
    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        faculties.put(facultyCounter, faculty);
        return faculty;
    }

    @Override
    public Faculty deleteFaculty(Long facultyId) {
        return faculties.remove(facultyId);
    }

    @Override
    public List<Faculty> facultyColorFilter(String color) {
        return faculties.values().stream()
                .filter(f -> (Objects.equals(f.getColor(), color)))
                .collect(Collectors.toList());
    }

}
