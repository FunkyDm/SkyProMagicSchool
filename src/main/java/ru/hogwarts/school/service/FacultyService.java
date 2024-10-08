package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFacultyById(Long facultyId);

    Faculty updateFaculty(Long facultyId, Faculty faculty);

    Faculty deleteFaculty(Long facultyId);

    List<Faculty> facultyColorFilter(String color);
}
