package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MissingStudentException extends RuntimeException{
    public MissingStudentException(long id) {
        super("Студент: %s не найден".formatted(id));
    }
}
