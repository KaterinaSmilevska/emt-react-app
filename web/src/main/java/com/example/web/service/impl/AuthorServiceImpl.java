package com.example.web.service.impl;

import com.example.web.model.Author;
import com.example.web.model.Country;
import com.example.web.model.exceptions.CountryNotFoundException;
import com.example.web.repository.AuthorRepository;
import com.example.web.repository.CountryRepository;
import com.example.web.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final CountryRepository countryRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, CountryRepository countryRepository) {
        this.authorRepository = authorRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return this.authorRepository.findById(id);
    }

    @Override
    public Optional<Author> save(String name, String surname, Long countryId) {
        Country country = this.countryRepository.findById(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId));

        Author author = new Author(name, surname, country);
        return Optional.of(this.authorRepository.save(author));
    }

}
