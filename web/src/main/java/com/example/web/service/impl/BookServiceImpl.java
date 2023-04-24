package com.example.web.service.impl;

import com.example.web.model.Author;
import com.example.web.model.Book;
import com.example.web.model.dto.BookDto;
import com.example.web.model.exceptions.AuthorNotFoundException;
import com.example.web.model.exceptions.BookNotFoundException;
import com.example.web.repository.AuthorRepository;
import com.example.web.repository.BookRepository;
import com.example.web.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return this.bookRepository.findById(bookId);
    }

    @Override
    public Optional<Book> save(BookDto bookDto) {
        Author author = this.authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(bookDto.getAuthorId()));

        Book book = new Book(bookDto.getName(), bookDto.getCategory(), author, bookDto.getAvailableCopies(), false);
        this.bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Long id, BookDto bookDto) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));


        Author author = this.authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(bookDto.getAuthorId()));

        book.setName(bookDto.getName());
        book.setCategory(bookDto.getCategory());
        book.setAuthor(author);
        book.setAvailableCopies(bookDto.getAvailableCopies());

        this.bookRepository.save(book);
        return Optional.of(book);
    }

    @Override
    public void deleteById(Long bookId) {
        this.bookRepository.deleteById(bookId);
    }

    @Override
    public Optional<Book> markAsTaken(Long bookId) {
        Book book = this.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        if(book.getAvailableCopies() > 0){
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            this.bookRepository.save(book);

            if(book.getAvailableCopies() == 0){
                book.setTaken(true);
                this.bookRepository.save(book);
            }

        }
        else{
            book.setTaken(true);
            this.bookRepository.save(book);
        }
        this.bookRepository.save(book);
        return Optional.of(book);
    }

}
