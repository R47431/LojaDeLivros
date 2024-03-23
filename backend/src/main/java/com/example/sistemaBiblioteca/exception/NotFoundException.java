package com.example.sistemaBiblioteca.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String messagem) {
        super(messagem);
    }
}

