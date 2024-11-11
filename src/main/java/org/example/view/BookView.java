package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.model.Book;
import org.example.view.model.BookDTO;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javax.swing.*;

import java.util.List;
//in presentation sau View NU e logica!
public class BookView {
    private TableView<BookDTO> bookTableView;
    private ObservableList<BookDTO> booksObservableList; //final, ca sa nu pierdem referinta setata in tabel si modificarile nu se vor mai vedea.
    //dar avem voie s adaugam, sa stergem, dar sa nu facem alta atribuire

    private TextField authorTextField;
    private TextField titleTextField;
    private Label authorLabel;
    private Label titleLabel;
    private Button saveButton;
    private Button deleteButton;

    public BookView(Stage primaryStage, List<BookDTO>books){
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);
        initTableView(gridPane);

        initSaveOptions(gridPane);

        primaryStage.show();
    }

    private void initializeGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }

    private void initTableView(GridPane gridPane) {
        bookTableView = new TableView<>();
        bookTableView.setPlaceholder(new Label("No books to display"));

        TableColumn<BookDTO, String> titleColumn = new TableColumn<BookDTO, String>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn<BookDTO, String> authorColumn = new TableColumn<BookDTO, String>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        bookTableView.getColumns().addAll(titleColumn, authorColumn);

        bookTableView.setItems(booksObservableList);

        gridPane.add(bookTableView,0, 0, 5, 1);
    }

    private void initSaveOptions(GridPane gridPane)
    {
        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);
        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        authorLabel = new Label("Author");
        gridPane.add(authorLabel, 3, 1);
        authorTextField = new TextField();
        gridPane.add(authorTextField, 4, 1);

        titleLabel = new Label("Title");
        gridPane.add(titleLabel, 1, 1);
        titleTextField = new TextField();
        gridPane.add(titleTextField, 2, 1);

        saveButton = new Button("Save");
        gridPane.add(saveButton, 5, 1);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 6, 1);
    }

    public void addSaveButtonListener(EventHandler<ActionEvent> saveButtonListener){
        saveButton.setOnAction(saveButtonListener);
    }
    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener){
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addDisplayAlertMessage (String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();  //va afisa si va astepta pana cand il inchid cu mana mea
    }
    public String getTitle(){
        return titleTextField.getText();
    }
    public String getAuthor(){
        return authorTextField.getText();
    }
    public void addBookToObserver(BookDTO bookDTO){
        this.booksObservableList.add(bookDTO);
    }
    public void removeBookFromObserver(BookDTO bookDTO){
        this.booksObservableList.remove(bookDTO);
    }

    public TableView<BookDTO> getBookTableView() {
        return bookTableView;
    }
}
