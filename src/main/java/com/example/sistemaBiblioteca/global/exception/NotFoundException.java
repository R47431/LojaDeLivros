package com.example.sistemaBiblioteca.global.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -5365630128856068164L;

    public NotFoundException() {
    }

    public NotFoundException(String s) {
        super(s);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
