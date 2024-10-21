package ru.hogwarts.school.service.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> facultiesRepository = new HashMap<>();

    private static long facultyCounter = 1L;

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
        facultiesRepository.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty getFacultyById(long id) {
        return facultiesRepository.get(id);
    }

    @Override
    public Faculty updateFaculty(long id, Faculty faculty) {
        if(!facultiesRepository.containsKey(id)){
            return null;
        }
        faculty.setId(id);
        facultiesRepository.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty deleteFaculty(long id) {
        return facultiesRepository.remove(id);
    }

    @Override
    public List<Faculty> filterFacultiesByColor(String color) {
        return facultiesRepository.values().stream()
                .filter(f -> (Objects.equals(f.getColor(), color)))
                .collect(Collectors.toList());
    }

}
