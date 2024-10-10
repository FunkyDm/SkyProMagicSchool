package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.createFaculty(faculty);
    }

    @GetMapping("/{facultyId}/get")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Faculty getFaculty(@PathVariable("facultyId") Long facultyId) {
        return facultyServiceImpl.getFacultyById(facultyId);
    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Faculty updateFaculty(@PathVariable("id") Long id, @RequestBody Faculty faculty) {
        return facultyServiceImpl.updateFaculty(id, faculty);
    }

    @DeleteMapping("/{facultyId}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFaculty(@PathVariable("facultyId") Long facultyId) {
        facultyServiceImpl.deleteFaculty(facultyId);
    }

    @GetMapping("/colors")
    public List<Faculty> filterFacultiesByColor(@RequestParam String facultyColor) {
        return facultyServiceImpl.filterFacultiesByColor(facultyColor);
    }

}
