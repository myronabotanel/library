package org.example.launcher;
import javafx.stage.Stage;
import org.example.database.DatabaseConnectionFactory;
import org.example.mapper.BookMapper;
import org.example.repository.book.BookRepository;
import org.example.repository.book.BookRepositoryMySQL;
import org.example.service.book.BookService;
import org.example.service.book.BookServiceImplementation;

import org.example.view.CustomerView;
import org.example.view.model.BookDTO;


import java.sql.Connection;
import java.util.List;

public class CustomerComponentFactory {
    private final CustomerView customerView;
    private final BookService bookService;
    private final BookRepository bookRepository;
    private static CustomerComponentFactory instance;

    public static CustomerComponentFactory getInstance(Boolean componentForTest, Stage stage) {
        if (instance == null) {
            instance = new CustomerComponentFactory(componentForTest, stage);
        }
        return instance;
    }


    private CustomerComponentFactory(Boolean componentsForTest, Stage stage) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryMySQL(connection);
        this.bookService = new BookServiceImplementation(bookRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(bookService.findAll());
        this.customerView = new CustomerView(stage, bookDTOs);
    }



    public CustomerView getCustomerView() {
        return customerView;
    }

    public BookService getBookService() {
        return bookService;
    }

}

