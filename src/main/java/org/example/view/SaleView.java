package org.example.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.model.Book;
import org.example.service.sale.SaleService;

import java.util.List;

public class SaleView {
    private ComboBox<Book> bookComboBox;
    private TextField priceTextField;
    private Button sellButton;
    private SaleService saleService;

    public SaleView(Stage primaryStage, SaleService saleService, List<Book> books) {
        this.saleService = saleService;

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sell Book");

        bookComboBox = new ComboBox<>();
        bookComboBox.getItems().addAll(books);
        gridPane.add(bookComboBox, 1, 1);

        priceTextField = new TextField();
        gridPane.add(priceTextField, 1, 2);

        sellButton = new Button("Sell Book");
        gridPane.add(sellButton, 1, 3);

        sellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Book selectedBook = bookComboBox.getValue();
                double price = Double.parseDouble(priceTextField.getText());
               // saleService.sellBook(selectedBook, price);
            }
        });

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setVgap(10);
        gridPane.setHgap(10);
    }
}
