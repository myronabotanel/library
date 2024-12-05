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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.view.model.UserDTO;
import org.example.view.model.builder.UserDTOBuilder;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import java.util.List;

public class AdminView {
    private TableView<UserDTO> userTableView;
    private ObservableList<UserDTO> usersObservableList;
    private TextField usernameTextField;
    private TextField passwordTextField;
    private Label usernameLabel;
    private Label passwordLabel;
    private Button saveButton;
    private Button deleteButton;
    private Button addButton;
    private Button viewBooksButton;
    private Text actiontarget;

    public AdminView(Stage primaryStage, List<UserDTO> userDTOS) {
        primaryStage.setTitle("Admin Panel");

        GridPane gridPane = new GridPane();
        initializeGridPane(gridPane);

        Scene scene = new Scene(gridPane, 720, 480);
        primaryStage.setScene(scene);

        usersObservableList = FXCollections.observableArrayList(userDTOS);
        initTableView(gridPane);
        initSaveOptions(gridPane);

        primaryStage.show();
    }

    private void initTableView(GridPane gridPane) {
        userTableView = new TableView<>();
        userTableView.setPlaceholder(new Label("No rows to display"));

        // Coloană pentru Username
        TableColumn<UserDTO, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Coloană pentru Role
        TableColumn<UserDTO, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Adăugăm toate coloanele în TableView
        userTableView.getColumns().addAll(usernameColumn, roleColumn);
        userTableView.setItems(usersObservableList);

        // Adăugăm TableView la grid
        gridPane.add(userTableView, 0, 0, 5, 1);

        actiontarget = new Text();
        actiontarget.setFill(Color.FIREBRICK);
        gridPane.add(actiontarget, 1, 6);
    }

    private void initSaveOptions(GridPane gridPane) {
        // Etichetă și câmp pentru Username
        usernameLabel = new Label("Username");
        gridPane.add(usernameLabel, 1, 1);

        usernameTextField = new TextField();
        gridPane.add(usernameTextField, 2, 1);

        // Etichetă și câmp pentru Role
        passwordLabel = new Label("Password");
        gridPane.add(passwordLabel, 3, 1);

        passwordTextField = new TextField();
        gridPane.add(passwordTextField, 4, 1);

        // Butoane Save și Delete
        saveButton = new Button("Save");
        gridPane.add(saveButton, 5, 1);

        deleteButton = new Button("Delete");
        gridPane.add(deleteButton, 6, 1);

        viewBooksButton = new Button("View Books");
        gridPane.add(viewBooksButton, 7, 1);


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
    public void addViewBooksButtonListener(EventHandler<ActionEvent> viewBooksButtonListener){
        viewBooksButton.setOnAction(viewBooksButtonListener);
    }
    public void setActionTargetText(String text){ this.actiontarget.setText(text);}


    public void addDeleteButtonListener(EventHandler<ActionEvent> deleteButtonListener) {
        deleteButton.setOnAction(deleteButtonListener);
    }

    public void addAddButtonListener(EventHandler<ActionEvent> addButtonListener) {
        addButton.setOnAction(addButtonListener);
    }

    public void addSelectionTableListener(ChangeListener selectionTableListener) {
        userTableView.getSelectionModel().selectedItemProperty().addListener(selectionTableListener);
    }

    public void displayAlertMessage(String titleInformation, String headerInformation, String contextInformation) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleInformation);
        alert.setHeaderText(headerInformation);
        alert.setContentText(contextInformation);

        alert.showAndWait();
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public ObservableList<UserDTO> getUsersObservableList() {
        return usersObservableList;
    }

    public void addUserToObservableList(UserDTO userDTO) {
        this.usersObservableList.add(userDTO);
    }

    public void removeUserFromObservableList(UserDTO userDTO) {
        this.usersObservableList.remove(userDTO);
    }

    public TableView getUserTableView() {
        return userTableView;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

}
