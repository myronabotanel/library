package org.example.controller;

import javafx.event.EventHandler;
import org.example.mapper.BookMapper;
import org.example.service.BookService;
import org.example.view.BookView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.view.model.BookDTO;
import org.example.view.model.builder.BookDTOBuilder;

public class BookController
{
    //de aici nu se acceseaza Book Repositoru
    private final BookView bookView;
    private final BookService bookService;
    public BookController(BookView bookView, BookService bookService){
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event)
        {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();

            if(title.isEmpty() || author.isEmpty())
            {
                bookView.addDisplayAlertMessage("Save Error", "Problem at Author or Title fields",  "Can not have an empty Title or Author field.");
            }else{
                BookDTO bookDTO = new BookDTOBuilder().setTitle(title).setAuthor(author).build();
                boolean savedBook = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));  //save sa salveze o carte, nu BookDTO. service trebuie sa primeasca un Book ca return tipe

                if(savedBook) {
                    bookView.addDisplayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                    bookView.addBookToObserver(bookDTO);
                }else{
                    bookView.addDisplayAlertMessage("Save Error", "Problem at adding Book", "There was a problem at adding the book to the database. Please try again!");
                }
            }
        }

    }

    private class DeleteButtonListener implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            BookDTO bookDTO = (BookDTO)bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null) {
                boolean deletionSuccessful = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if(deletionSuccessful){
                    bookView.addDisplayAlertMessage("Delete Successful", "Book Deleted", "Book was successfully deleted from the database.");
                    bookView.removeBookFromObserver(bookDTO);
                }else {
                    bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book", "There was a problem with the database. Please try again!");
                }
            }else{
                bookView.addDisplayAlertMessage("Delete Error", "Problem at deleting book", "You must select a book before pressing the delete button.");
            }
        }
    }

}
