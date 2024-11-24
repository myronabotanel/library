package org.example.view;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.view.model.BookDTO;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javax.swing.*;

import java.util.List;

public class BookView {
    private TableView<BookDTO> bookTableView;
    private ObservableList<BookDTO> booksObservableList;
    private TextField authorTextField;
    private TextField titleTextField;
    private Label authorLabel;
    private Label titleLabel;
    private Button saveButton;
    private Button deleteButton;

    public BookView(Stage primaryStage, List<BookDTO> bookDTOS){
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(bookDTOS);
        initTableView(gridPane);

        initSaveOptions(gridPane);

        primaryStage.show();
    }

    private void initTableView(GridPane gridPane) {
        bookTableView = new TableView<BookDTO>();
        bookTableView.setPlaceholder(new Label("No rows to display"));

        // Coloană pentru Titlu
        TableColumn<BookDTO, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        // Coloană pentru Autor
        TableColumn<BookDTO, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        // Coloană pentru Preț
        TableColumn<BookDTO, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Coloană pentru Stoc (cantitate)
        TableColumn<BookDTO, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Coloană pentru butonul de vânzare
        TableColumn<BookDTO, Void> saleColumn = new TableColumn<>("Sale");
        saleColumn.setCellFactory(col -> {
            TableCell<BookDTO, Void> cell = new TableCell<>() {
                private final Button saleButton = new Button("Sell");

                {
                    saleButton.setOnAction(event -> {
                        BookDTO bookDTO = getTableView().getItems().get(getIndex());
                        showSaleDialog(bookDTO);
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(saleButton);
                    }
                }
            };
            return cell;
        });

        // Adăugăm toate coloanele în TableView
        bookTableView.getColumns().addAll(titleColumn, authorColumn, priceColumn, stockColumn, saleColumn);
        bookTableView.setItems(booksObservableList);

        // Adăugăm TableView la grid
        gridPane.add(bookTableView, 0, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane){
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);

        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 3, 1);

        authorTextField = new TextField();
        gridPane.add(authorTextField, 4, 1);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 5, 1);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 6, 1);
    }

    private void initializeGridPane(GridPane gridPane){
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }

    public void addSelectionTableListener(ChangeListener selectionTableListener){
        bookTableView.getSelectionModel().selectedItemProperty().addListener(selectionTableListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void displayAlertMessage(String titleInformation, String headerInformation, String contextInformation){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleInformation);
        alert.setHeaderText(headerInformation);
        alert.setContentText(contextInformation);

        alert.showAndWait();
    }

    public String getTitle(){
        return titleTextField.getText();
    }

    public String getAuthor(){
        return authorTextField.getText();
    }

    public ObservableList<BookDTO> getBooksObservableList(){
        return booksObservableList;
    }

    public void addBookToObservableList(BookDTO bookDTO){
        this.booksObservableList.add(bookDTO);
    }

    public void removeBookFromObservableList(BookDTO bookDTO){
        this.booksObservableList.remove(bookDTO);
    }

    public TableView getBookTableView(){
        return bookTableView;
    }

    private void showSaleDialog(BookDTO bookDTO) {
        // Creăm un dialog pentru a introduce cantitatea de vânzare
        TextInputDialog quantityDialog = new TextInputDialog();
        quantityDialog.setTitle("Sell Book");
        quantityDialog.setHeaderText("Enter quantity for " + bookDTO.getTitle());
        quantityDialog.setContentText("Quantity:");

        quantityDialog.showAndWait().ifPresent(quantityText -> {
            try {
                // Încearcă să convertești textul introdus într-un număr întreg
                int quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    displayAlertMessage("Invalid Quantity", "The quantity must be positive", "Please enter a valid quantity.");
                    return;
                }

                // Verifică dacă există stoc suficient
//                if (bookDTO.getStock() < quantity) {
//                    displayAlertMessage("Insufficient Stock", "Not enough stock available", "The stock is lower than the requested quantity.");
//                    return;
//                }

                // Realizează vânzarea
                //saleService.sellBook(bookDTO, quantity); // Procesarea vânzării

                // Actualizează stocul cărții
                //bookDTO.setStock(bookDTO.getStock() - quantity);

                // Actualizează tabelul
                bookTableView.refresh();

            } catch (NumberFormatException e) {
                displayAlertMessage("Invalid Input", "Please enter a valid number for the quantity", "The quantity must be a valid integer.");
            }
        });
    }


}
