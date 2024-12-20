package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyServiceImpl facultyService;

    public FacultyController(FacultyServiceImpl facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<Faculty> getFaculty(@PathVariable("id") long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable("id") long id,
                                                 @RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.updateFaculty(id, faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("/{id}/remove")
    public ResponseEntity<Void> deleteFaculty(@PathVariable("id") long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/colors")
    public ResponseEntity<List<Faculty>> filterFacultiesByColor(@RequestParam String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.filterFacultiesByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/colors-or-name")
    public List<Faculty> getFacultyByColorIgnoreCaseOrNameIgnoreCase(@RequestParam(value = "color", required = false) String color,
                                                                     @RequestParam(value = "name", required = false) String name) {
        return facultyService.getFacultyByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    @GetMapping("/{id}/get/students")
    public List<Student> findFacultyByStudentId(@PathVariable("id") long id) {
        return facultyService.findByFacultyId(id);
    }

    @GetMapping("/get/longest-name")
    public String findFacultyLongestName() {
        return facultyService.findFacultyLongestName();
    }

}
