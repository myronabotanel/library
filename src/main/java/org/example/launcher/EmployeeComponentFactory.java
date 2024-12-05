package org.example.launcher;

import javafx.stage.Stage;
import org.example.controller.BookController;
import org.example.database.DatabaseConnectionFactory;
import org.example.mapper.BookMapper;
import org.example.repository.book.BookRepository;
import org.example.repository.book.BookRepositoryCacheDecorator;
import org.example.repository.book.BookRepositoryMySQL;
import org.example.repository.book.Cache;
import org.example.repository.sale.SaleRepository;
import org.example.repository.sale.SaleRepositoryMySQL;
import org.example.service.book.BookService;
import org.example.service.book.BookServiceImplementation;
import org.example.service.sale.SaleService;
import org.example.service.sale.SaleServiceImplementation;
import org.example.view.BookView;
import org.example.view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class EmployeeComponentFactory
{
    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final SaleService saleService;
    private final SaleRepository saleRepository;
    private static EmployeeComponentFactory instance;

    public static EmployeeComponentFactory getInstance(Boolean componentsForTest, Stage stage){
        if (instance == null){
            instance = new EmployeeComponentFactory(componentsForTest, stage);
        }
        return instance;
    }

    public EmployeeComponentFactory(Boolean componentsForTest, Stage stage){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySQL(connection), new Cache<>());
        this.saleRepository = new SaleRepositoryMySQL(connection);
        this.bookService = new BookServiceImplementation(bookRepository);
        this.saleService = new SaleServiceImplementation(saleRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(this.bookService.findAll());
        this.bookView = new BookView(stage, bookDTOs);
        this.bookController = new BookController(bookView, bookService, saleService);
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

    public static EmployeeComponentFactory getInstance() {
        return instance;
    }
}