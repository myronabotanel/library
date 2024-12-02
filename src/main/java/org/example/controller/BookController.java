package org.example.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import org.example.mapper.BookMapper;
import org.example.service.book.BookService;
import org.example.view.BookView;
import javafx.event.ActionEvent;
import org.example.view.model.BookDTO;
import org.example.view.model.builder.BookDTOBuilder;

import java.util.List;


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
    private void refreshTableData() {
        // Obținem toate cărțile din baza de date
        List<BookDTO> allBooks = bookService.findAll().stream()
                .map(BookMapper::convertBookToBookDTO)
                .toList();

        // Actualizăm lista observabilă utilizată de TableView
        bookView.getBooksObservableList().setAll(allBooks);

        // Reîmprospătăm explicit tabelul pentru a reflecta modificările
        bookView.getBookTableView().refresh();
    }


    private class SaveButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String priceText = bookView.getPrice();
            String stockText = bookView.getStock();

            double price;
            int stock;
            try {
                price = Double.parseDouble(priceText);
                stock = Integer.parseInt(stockText);

                if (price < 0 || stock < 0) {
                    throw new IllegalArgumentException("Price and stock must be non-negative.");
                }
            } catch (IllegalArgumentException e) {
                bookView.displayAlertMessage(
                        "Save Error",
                        "Invalid Price or Stock",
                        "Price must be a valid number, and Stock must be a valid whole number. Both must be non-negative."
                );
                return;
            }

            if (title.isEmpty() || author.isEmpty()) {
                bookView.displayAlertMessage("Save Error", "Problem at Title or Author fields",
                        "Cannot have empty Author or Title fields. Please fill in the fields before submitting Save!");
            } else {
                BookDTO bookDTO = new BookDTOBuilder()
                        .setAuthor(author)
                        .setTitle(title)
                        .setPrice(price)
                        .setStock(stock)
                        .build();

                boolean bookExists = bookService.save(BookMapper.convertBookDTOToBook(bookDTO));
                if (bookExists) {
                    bookView.displayAlertMessage("Save Successful", "Stock Updated",
                            "The book already exists. Stock was successfully updated.");
                } else {
                    bookView.displayAlertMessage("Save Successful", "Book Added",
                            "Book was successfully added to the database.");
                }

                // Reîmprospătăm tabelul pentru a reflecta schimbările
                refreshTableData();
            }
        }
    }



    private class SelectionTableListener implements ChangeListener<BookDTO> {
        @Override
        public void changed(ObservableValue<? extends BookDTO> observable, BookDTO oldValue, BookDTO newValue) {
            if (newValue != null) {
                // Accesăm detalii doar dacă există un element selectat
                System.out.println("Selected Book Author: " + newValue.getAuthor() + ", Title: " + newValue.getTitle());
            } else {
                // În cazul în care selecția este eliminată
                System.out.println("No book selected.");
            }
        }
    }


    private class DeleteButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();  //return idemul selectat
            if (bookDTO != null){
                boolean deletionSuccessfull = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO)); //service nu accepta DTO
                if (deletionSuccessfull){
                    bookView.removeBookFromObservableList(bookDTO);  //stergem si din ObservebleList, ca sa nu l mai afiseze
                } else {
                    bookView.displayAlertMessage("Deletion not successful :(", "Deletion Process", "There was a problem in the deletion process. Please restart the application and try again!");
                }
            } else {  //nu a fost selectat nici un item
                bookView.displayAlertMessage("Deletion not successful :(", "Deletion Process :(", "You need to select a row from table before pressing the delete button! :(");
            }
        }
    }
}