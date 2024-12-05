package org.example.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.QuadCurve;
import org.example.mapper.BookMapper;
import org.example.model.Sale;
import org.example.service.book.BookService;
import org.example.service.sale.SaleService;
import org.example.service.sale.SaleServiceImplementation;
import org.example.service.user.CurrentUserService;
import org.example.view.BookView;
import javafx.event.ActionEvent;
import org.example.view.model.BookDTO;
import org.example.view.model.builder.BookDTOBuilder;

import java.time.LocalDateTime;
import java.util.List;


public class BookController
{
    //de aici nu se acceseaza Book Repository
    private final BookView bookView;
    private final BookService bookService;
    private final SaleService saleService;

    public BookController(BookView bookView, BookService bookService, SaleService saleService){
        this.bookView = bookView;
        this.bookService = bookService;
        this.saleService = saleService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addSelectionTableListener(new SelectionTableListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());
        this.bookView.addSellButtonListener(new SellButtonListener());
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
    private class SellButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();  //return idemul selectat
            if (bookDTO != null) {
                TextInputDialog quantityDialog = new TextInputDialog();
                quantityDialog.setTitle("Sell Book");
                quantityDialog.setHeaderText("Enter quantity to sell for: " + bookDTO.getTitle());
                quantityDialog.setContentText("Quantity:");

                String title = bookDTO.getTitle();
                String author = bookDTO.getAuthor();
                double price = bookDTO.getPrice();
                int stock = bookDTO.getStock();

                quantityDialog.showAndWait().ifPresent(quantityText -> {
                    try {
                        int quantity = Integer.parseInt(quantityText);

                        if (quantity <= 0) {
                            bookView.displayAlertMessage("Invalid Quantity", "Error", "The quantity must be a positive number.");
                            return;
                        }

                        if (quantity > stock) {
                            bookView.displayAlertMessage("Insufficient Stock", "Error",
                                    "Not enough stock available. Current stock: " + bookDTO.getStock());
                            return;
                        }
                        double totalPrice = price * quantity;

                        bookDTO.setStock(bookDTO.getStock() - quantity);
                        // Actualizăm stocul
                        boolean success = bookService.update(BookMapper.convertBookDTOToBook(bookDTO)); // Salvează modificarea stocului
                        System.out.println(String.format("Stock actualizat: %d", stock - quantity));
                        // Reîmprospătăm tabelul
                        refreshTableData();
                        if (success) {
                            Sale sale = new Sale(
                                    null, //id generat in baza de date
                                    title,
                                    CurrentUserService.getCurrentUsername(),
                                    quantity,
                                    totalPrice,
                                    LocalDateTime.now()
                            );
                            boolean saleSaved = saleService.save(sale); //salvam vanzarea
                            bookView.displayAlertMessage("Sale Successful", "Book Sold",
                                    "Successfully sold " + quantity + " copies of " + bookDTO.getTitle());
                        }
                    }catch (NumberFormatException e) {
                        bookView.displayAlertMessage("Invalid Input", "Error",
                                "Please enter a valid number for the quantity.");
                    }
                });
            } else {
                bookView.displayAlertMessage("No Selection", "Sell Process",
                        "You need to select a book before pressing the Sell button.");
            }
        }
    }

}