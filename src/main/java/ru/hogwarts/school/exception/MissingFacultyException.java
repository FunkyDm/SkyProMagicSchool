package ru.hogwarts.school.exception;

public class MissingFacultyException extends RuntimeException{
    public MissingFacultyException(String message) {
        super(message);
    }
}
