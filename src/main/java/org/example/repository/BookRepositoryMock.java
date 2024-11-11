package org.example.repository;

import org.example.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository
{
    private final List<Book>books;  //folosim List pentru a folosi interfete. ArrayList e o clasa, o implementare
    public BookRepositoryMock(){
        books = new ArrayList<>(); //aici tinem cartile
    }


    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
         //Optional.empty() - inlocuitorul pentru null
        return books.parallelStream()    //precum curgerae apei, vin una cate una
                .filter(it -> it.getId().equals(id))  //importand sa folosim equals deoarece avem Long. un obj.
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public boolean delete(Book book) {
        return books.remove(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }
}
