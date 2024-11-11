package org.example.launcher;

import javafx.stage.Stage;
import org.example.controller.BookController;
import org.example.database.DatabaseConnectionFactory;
import org.example.mapper.BookMapper;
import org.example.model.Book;
import org.example.repository.BookRepository;
import org.example.repository.BookRepositoryMySQL;
import org.example.service.BookService;
import org.example.service.BookServiceImplementation;
import org.example.view.BookView;
import org.example.view.model.BookDTO;

import javax.sound.sampled.BooleanControl;
import java.sql.Connection;
import java.util.*;

//singleton
public class ComponentFactory {
    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;

    private static ComponentFactory instance;
    public static ComponentFactory getInstance(Boolean componentForTest, Stage primaryStage){
        //tema ca singleton sa fie thread safe si cu lazy load
        if (instance == null)
        {
            instance = new ComponentFactory(componentForTest, primaryStage);
        }
        return instance;
    }
    public ComponentFactory(Boolean componentsForTest, Stage primaryStage){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImplementation(bookRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.bookView = new BookView(primaryStage, bookDTOs);
        this.bookController = new BookController(bookView, bookService);
    }
    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

}
