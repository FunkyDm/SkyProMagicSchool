package ru.hogwarts.school.service.impl;

import jakarta.annotation.PostConstruct;
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
    private final Map<Long, Faculty> faculties = new HashMap<>();

    private static Long facultyCounter = 1L;

    @PostConstruct
    public void init(){
        createFaculty(new Faculty("Первый", "Красный"));
        createFaculty(new Faculty("Второй", "Зеленый"));
        createFaculty(new Faculty("Третий", "Оранжевый"));
        createFaculty(new Faculty("Четвертый", "Сиреневый"));
        createFaculty(new Faculty("Пятый", "Синий"));
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(facultyCounter++);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyById(Long facultyId) {
        return faculties.get(facultyId);
    }

    @Override
    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        faculty.setId(facultyId);
        faculties.put(facultyId, faculty);
        return faculty;
    }

    @Override
    public Faculty deleteFaculty(Long facultyId) {
        return faculties.remove(facultyId);
    }

    @Override
    public List<Faculty> filterFacultiesByColor(String color) {
        return faculties.values().stream()
                .filter(f -> (Objects.equals(f.getColor(), color)))
                .collect(Collectors.toList());
    }

}
