package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFacultyById(long id);

    Faculty updateFaculty(long id, Faculty faculty);

    Faculty deleteFaculty(long id);

    List<Faculty> filterFacultiesByColor(String color);
}
