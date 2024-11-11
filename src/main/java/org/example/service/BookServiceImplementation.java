package org.example.service;

import org.example.model.Book;
import org.example.repository.BookRepository;
import org.example.repository.BookRepositoryMock;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookServiceImplementation implements BookService
{
    private final BookRepository bookRepository;

    public BookServiceImplementation(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id: %d was not found!".formatted(id))); //daca avem cartea, o return, daca nu, arunca o exceptie

    }

    @Override
    public boolean save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean delete(Book book) {
        return bookRepository.delete(book);
    }
//E BINE CA INTR O CLASA SA FIE UN PUNCT DE INTRARE PENTRU O IMPLEMENTARE.
//SA NU FAC UN LUCRU DE 2 ORI, CI SA MA FOLOSESC DE IMPLEMENT
    @Override
    public int getAgeOfBook(Long id) {
        Book book = this.findById(id); //asa ma folosesc de implemenatrea facuta
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }
}
