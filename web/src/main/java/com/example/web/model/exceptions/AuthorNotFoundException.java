package com.example.web.model.exceptions;


public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Long authorId) {
    }
}
