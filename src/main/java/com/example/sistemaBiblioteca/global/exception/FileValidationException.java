package com.example.sistemaBiblioteca.global.exception;

public class FileValidationException extends RuntimeException{
    public FileValidationException(String message) {
        super(message);
    }
}
