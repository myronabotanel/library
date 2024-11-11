package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import org.example.model.Book;
import javafx.stage.*;
import org.example.view.model.BookDTO;import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;



import javax.swing.text.TableView;
import java.awt.*;
import java.util.List;

public class BookView
{
    private TableView bookTableView;
    private ObservableList<BookDTO>booksObservableList;
    //Data Transfer Object. (ex: informatii confidentiale in DB, pe care nu vrem sa se vada in interfata. aici vom scrie exact ce vrem sa se vada in interfata. ex: pot face sa nu se vada id ul.
    //StringProperty
    //ObservebleList:
    private TextField authorTextField;
    private TextField titleTextField;
    private Label authorLabel;
    private  Label titleLabel;
    private Button saveButton;
    private Button deleteButton;
    public BookView(Stage primaryStage, List<Book>books)
    {
        primaryStage.setTitle("Library");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);
        //primaryStage contine scena, iar scena contine gridPane
        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        booksObservableList = FXCollections.observableArrayList(books);
        initTableView(gridPane);
    }

    private void initializeGridPane(GridPane gridPane)
    {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
    }
    private void initTableView(GridPane gridPane) {
        bookTableView = new TableView<BookDTO>();
        bookTableView.setPlaceholder(new Label("No books to display"));
        


    }
}
