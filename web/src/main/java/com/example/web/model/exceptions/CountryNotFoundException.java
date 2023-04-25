package com.example.web.model.exceptions;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException(Long countryId) {
        super(String.format("Country with id: %d is not found", countryId));
    }
}
