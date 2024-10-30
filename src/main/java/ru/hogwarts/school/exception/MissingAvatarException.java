package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MissingAvatarException extends RuntimeException {
    public MissingAvatarException(long id) {
        super("Аватар студента: %s не найден".formatted(id));
    }
}
