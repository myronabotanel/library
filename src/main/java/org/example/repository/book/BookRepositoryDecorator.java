package org.example.repository.book;

import org.example.repository.book.BookRepository;

public abstract class BookRepositoryDecorator implements BookRepository
{
    //ca sa putem folosi aceasta clasa si pentru alti decoratori, clasa e abstracta, pt a o putea extinde la ce am nevoie
    protected BookRepository decoratedBookRepository;
    //protected => pot folosi campul meu atat in subclase, cat si in celelalte clase ce apartin aceluiasi pachet
    public BookRepositoryDecorator(BookRepository bookRepository){
        decoratedBookRepository = bookRepository;
    }
}
