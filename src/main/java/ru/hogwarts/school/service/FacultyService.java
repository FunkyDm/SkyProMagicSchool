package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFacultyById(long id);

    Faculty updateFaculty(long id, Faculty faculty);

    Faculty deleteFaculty(long id);

    List<Faculty> filterFacultiesByColor(String color);

    List<Faculty> getFacultyByColorIgnoreCaseOrNameIgnoreCase(String color, String name);

    List<Student> findByFacultyId(long id);

}
