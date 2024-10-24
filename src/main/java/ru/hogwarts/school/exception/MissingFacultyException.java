package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MissingFacultyException extends RuntimeException{
    public MissingFacultyException(long id) {
        super("Факультет: %s не найден".formatted(id));
    }
}
