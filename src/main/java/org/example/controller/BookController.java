package org.example.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import org.example.mapper.BookMapper;
import org.example.model.Book;
import org.example.model.builder.BookBuilder;
import org.example.service.book.BookService;
import org.example.view.BookView;
import javafx.event.ActionEvent;
import org.example.view.model.BookDTO;
import org.example.view.model.builder.BookDTOBuilder;



public class BookController
{
    //de aici nu se acceseaza Book Repository
    private final BookView bookView;
    private final BookService bookService;

    public BookController(BookView bookView, BookService bookService){
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addSelectionTableListener(new SelectionTableListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String priceString = bookView.getPrice();

            if (title.isEmpty() || author.isEmpty() || priceString.isEmpty()) {
                bookView.displayAlertMessage("Save Error", "Input Error", "All fields must be filled. Please fill in the title, author, and price before submitting.");
                return;
            }

            try {
                double price = Double.parseDouble(priceString);

                BookDTO bookDTO = new BookDTOBuilder()
                        .setAuthor(author)
                        .setTitle(title)
                        .setPrice(price)
                        .setStock(1) // Setăm stocul inițial ca 1
                        .build();
                Book book = new BookBuilder()
                        .setAuthor(author)
                        .setTitle(title)
                        .setPrice(price)
                        .setStock(1) // Setăm stocul inițial ca 1
                        .build();
                boolean savedBook = bookService.save(book);

                if (savedBook) {
                    bookView.displayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    bookView.addBookToObservableList(bookDTO);
                } else {
                    bookView.displayAlertMessage("Save Not Successful", "Book was not added", "There was a problem adding the book to the database.");
                }
            } catch (NumberFormatException e) {
                bookView.displayAlertMessage("Save Error", "Input Error", "Price must be a valid number.");
            }
        }
    }


    private class SelectionTableListener implements ChangeListener {

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            BookDTO selectedBookDTO = (BookDTO) newValue;
            System.out.println("Book Author: " + selectedBookDTO.getAuthor() + " Title: " + selectedBookDTO.getTitle());
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null){
                boolean deletionSuccessfull = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if (deletionSuccessfull){
                    bookView.removeBookFromObservableList(bookDTO);
                } else {
                    bookView.displayAlertMessage("Deletion not successful", "Deletion Process", "There was a problem in the deletion process. Please restart the application and try again!");
                }
            } else {
                bookView.displayAlertMessage("Deletion not successful", "Deletion Process", "You need to select a row from table before pressing the delete button!");
            }
        }
    }
}