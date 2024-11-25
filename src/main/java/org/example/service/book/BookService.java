package org.example.service.book;

import org.example.model.Book;

import java.util.List;

//metodele pe care le va putea folosi presentation
public interface BookService
{
    List<Book> findAll();
    Book findById(Long id); //daca am gasit cartea se va solutiona in int service pe baza a ceea ce s a solutionat in repository. aici gestionam toate situatiile
    boolean save (Book book);
    boolean delete (Book book);

    int getAgeOfBook (Long id); //e in service deoarece implica, si in mica masura, de logica.Repository se ocupa doar cu citire din baza de date
}