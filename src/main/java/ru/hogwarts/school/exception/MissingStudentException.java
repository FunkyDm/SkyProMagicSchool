package ru.hogwarts.school.exception;

public class MissingStudentException extends RuntimeException{
    public MissingStudentException(String message) {
        super(message);
    }
}
