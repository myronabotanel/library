package org.example;

import org.example.database.DatabaseConnectionFactory;
import org.example.model.*;
import org.example.model.builder.*;
import org.example.repository.book.BookRepository;
import org.example.repository.book.BookRepositoryCacheDecorator;
import org.example.repository.book.BookRepositoryMySQL;
import org.example.repository.book.Cache;
import org.example.repository.security.RightsRolesRepository;
import org.example.repository.security.RightsRolesRepositoryMySQL;
import org.example.repository.user.UserRepository;
import org.example.repository.user.UserRepositoryMySQL;
import org.example.service.book.BookService;
import org.example.service.book.BookServiceImplementation;
import org.example.service.user.AuthenticationService;
import org.example.service.user.AuthenticationServiceImpl;

import java.sql.Connection;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args)
    {
        Book book = new BookBuilder()
                .setTitle("Ion")
                .setAuthor("Liviu Rebreanu")
                .setPublishedDate(LocalDate.of(1910, 10, 21))
                .build();

        System.out.println("Miro");
        System.out.println(book);
//
//        BookRepository bookRepository = new BookRepositoryMock();
//        bookRepository.save(book);
//        bookRepository.save(new BookBuilder()
//                        .setAuthor("Ion Luca Karagiale")
//                        .setTitle("O scrisoare pierduta")
//                        .setPublishedDate(LocalDate.of(1884, 12, 18))
//                        .build());
//        bookRepository.removeAll();
//        System.out.println(bookRepository.findAll());
        //setam conexiunea


        //TREBUIE CREATE PT TOT PROIECTUL SI INJECTATE DIN EXTERIOR
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(false).getConnection();
        BookRepository bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySQL(connection), new Cache<>());
        BookService bookService = new BookServiceImplementation(bookRepository);

        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository, rightsRolesRepository);

        authenticationService.register("Miro", "miro");
        System.out.println(authenticationService.login("Miro", "miro"));

        bookService.save(book);
        System.out.println(bookService.findAll());
//        bookService.save(book);
//        System.out.println(bookService.findAll());
//        Book bookScrisoare = new BookBuilder()
//                        .setAuthor("Ion Luca Karagiale")
//                        .setTitle("O scrisoare pierduta")
//                        .setPublishedDate(LocalDate.of(1884, 12, 18))
//                        .build();
//        bookService.save(bookScrisoare);
//        System.out.println(bookService.findAll());
//        bookService.delete(bookScrisoare);
//        System.out.println(bookService.findAll());
//        bookService.save(book);
//        System.out.println(bookService.findAll());
//        bookService.delete(book);
//        System.out.println(bookService.findAll());


    }
}