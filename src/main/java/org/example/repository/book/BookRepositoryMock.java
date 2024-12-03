package org.example.repository.book;

import org.example.model.Book;
import org.example.repository.book.BookRepository;

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
        for (Book existingBook : books) {
            if (existingBook.getAuthor().equals(book.getAuthor()) &&
                    existingBook.getTitle().equals(book.getTitle()) &&
                    existingBook.getPrice() == book.getPrice()) {
                // Actualizăm stocul
                existingBook.setStock(existingBook.getStock() + book.getStock());
                return true;
            }
        }
        // Adăugăm cartea ca nouă
        books.add(book);
        return true;
    }


    @Override
    public boolean delete(Book book) {
        return books.remove(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public boolean update(Book book) {
        return false;
    }
}