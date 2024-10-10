package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyServiceImpl facultyServiceImpl;

    public FacultyController(FacultyServiceImpl facultyServiceImpl) {
        this.facultyServiceImpl = facultyServiceImpl;
    }

    @PostMapping("/add")
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.createFaculty(faculty);
    }

    @GetMapping("/{facultyId}/get")
    public Faculty getFaculty(@PathVariable("facultyId") Long facultyId) {
        return facultyServiceImpl.getFacultyById(facultyId);
    }

    @PutMapping("/update")
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.updateFaculty(faculty.getId(), faculty);
    }

    @DeleteMapping("/{facultyId}/remove")
    public void deleteFaculty(@PathVariable("facultyId") Long facultyId) {
        facultyServiceImpl.deleteFaculty(facultyId);
    }

    @GetMapping("/colors")
    public List<Faculty> filterFacultiesByColor(@RequestParam String facultyColor) {
        return facultyServiceImpl.filterFacultiesByColor(facultyColor);
    }

}
