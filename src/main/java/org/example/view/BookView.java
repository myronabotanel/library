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

import java.util.List;

public class BookView {
    private TableView<BookDTO> bookTableView;
    private ObservableList<BookDTO> booksObservableList;
    private TextField authorTextField;
    private TextField titleTextField;
    private TextField priceField;
    private TextField stockField;
    private Label priceLabel;
    private Label stockLabel;
    private Label authorLabel;
    private Label titleLabel;
    private Button saveButton;
    private Button deleteButton;
    private Button sellButton;

    public BookView(Stage primaryStage, List<BookDTO> bookDTOS) {
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> NavigationManager.goBack(primaryStage));
        gridPane.add(backButton, 0, 3); // Adaugă sub tabel

        booksObservableList = FXCollections.observableArrayList(bookDTOS);
        initTableView(gridPane);
        initSaveOptions(gridPane);

        primaryStage.show();
    }

    private void initTableView(GridPane gridPane) {
        bookTableView = new TableView<>();
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

        // Adăugăm toate coloanele în TableView
        bookTableView.getColumns().addAll(titleColumn, authorColumn, priceColumn, stockColumn);
        bookTableView.setItems(booksObservableList);

        // Adăugăm TableView la grid
        gridPane.add(bookTableView, 0, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane) {
        // Etichetă și câmp pentru Title
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);

        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        // Etichetă și câmp pentru Author
        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 3, 1);

        authorTextField = new TextField();
        gridPane.add(authorTextField, 4, 1);

        // Etichetă și câmp pentru Price
        priceLabel = new Label("Price");
        gridPane.add(priceLabel, 1, 2);

        priceField = new TextField();
        gridPane.add(priceField, 2, 2);

        // Etichetă și câmp pentru Stock
        stockLabel = new Label("Stock");
        gridPane.add(stockLabel, 3, 2);

        stockField = new TextField();
        gridPane.add(stockField, 4, 2);

        // Butoane Save și Delete
        saveButton = new Button("Save");
        gridPane.add(saveButton, 5, 2);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 6, 2);

        sellButton = new Button("Sell");
        gridPane.add(sellButton, 7, 2);
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener) {
        saveButton.setOnAction(saveButtonListener);
    }

    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addSellButtonListener(EventHandler<ActionEvent> sellButtonListener) {
        sellButton.setOnAction(sellButtonListener);
    }

    public void addSelectionTableListener(ChangeListener selectionTableListener) {
        bookTableView.getSelectionModel().selectedItemProperty().addListener(selectionTableListener);
    }

    public void displayAlertMessage(String titleInformation, String headerInformation, String contextInformation) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleInformation);
        alert.setHeaderText(headerInformation);
        alert.setContentText(contextInformation);

        alert.showAndWait();
    }

    public String getTitle() {
        return titleTextField.getText();
    }

    public String getAuthor() {
        return authorTextField.getText();
    }

    public String getPrice() {
        return priceField.getText();
    }

    public String getStock() {
        return stockField.getText();
    }

    public ObservableList<BookDTO> getBooksObservableList() {
        return booksObservableList;
    }

    public void addBookToObservableList(BookDTO bookDTO) {
        this.booksObservableList.add(bookDTO);
    }

    public void removeBookFromObservableList(BookDTO bookDTO) {
        this.booksObservableList.remove(bookDTO);
    }

    public TableView getBookTableView() {
        return bookTableView;
    }
}
