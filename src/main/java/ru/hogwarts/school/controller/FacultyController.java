package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Collection;
import java.util.Collections;
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

    @GetMapping("/{id}/get")
    //@ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Faculty> getFaculty(@PathVariable("id") Long id) {
        Faculty faculty = facultyServiceImpl.getFacultyById(id);
        if(faculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("/{id}/update")
    //@ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Faculty> updateFaculty(@PathVariable("id") Long id,
                                                 @RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyServiceImpl.updateFaculty(id,faculty);
        if(foundFaculty == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("/{id}/remove")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteFaculty(@PathVariable("id") Long id) {
        facultyServiceImpl.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/colors")
    public ResponseEntity<List<Faculty>> filterFacultiesByColor(@RequestParam String color) {
        if(color != null && !color.isBlank()){
            return ResponseEntity.ok(facultyServiceImpl.filterFacultiesByColor(color));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

}
