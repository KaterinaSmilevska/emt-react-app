package com.example.web.model.exceptions;


public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Long authorId) {
        super(String.format("Author with id: %d is not found", authorId));
    }
}
