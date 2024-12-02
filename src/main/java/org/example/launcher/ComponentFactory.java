package org.example.launcher;

import javafx.stage.Stage;
import org.example.controller.BookController;
import org.example.database.DatabaseConnectionFactory;
import org.example.mapper.BookMapper;
import org.example.repository.book.BookRepository;
import org.example.repository.book.BookRepositoryMySQL;
import org.example.service.book.BookService;
import org.example.service.book.BookServiceImplementation;
import org.example.view.BookView;
import org.example.view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class ComponentFactory {
    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;

    private static volatile ComponentFactory instance;
    //Singleton

    // Constructorul este privat pentru a preveni instanțierea din afara clasei
    private ComponentFactory(Boolean componentsForTest, Stage primaryStage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImplementation(bookRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.bookView = new BookView(primaryStage, bookDTOs);
        this.bookController = new BookController(bookView, bookService);
    }

    // Metoda publică sincronizată care obține instanța singleton
    public static ComponentFactory getInstance(Boolean componentForTest, Stage primaryStage) {
        if (instance == null) {  // Prima verificare fără blocare
            synchronized (ComponentFactory.class) {
                if (instance == null) {  // A doua verificare, după blocare
                    instance = new ComponentFactory(componentForTest, primaryStage);
                }
            }
        }
        return instance;
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

    ///////////////////////////////

}