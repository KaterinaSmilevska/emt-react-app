package com.example.web.service;

import com.example.web.model.Book;
import com.example.web.model.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findById(Long bookId);

    Optional<Book> save(BookDto bookDto);

    Optional<Book> edit(Long id, BookDto bookDto);

    void deleteById(Long bookId);

    Optional<Book> markAsTaken(Long bookId);
}
