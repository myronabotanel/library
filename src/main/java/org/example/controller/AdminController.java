package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import org.example.launcher.EmployeeComponentFactory;
import org.example.launcher.LoginComponentFactory;
import org.example.model.validator.Notification;
import org.example.service.admin.AdminService;
import org.example.service.user.AuthenticationService;
import org.example.view.AdminView;
import org.example.view.model.UserDTO;
import org.example.view.model.builder.UserDTOBuilder;
import org.example.model.Role;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController
{
    private final AdminView adminView;
    private final AdminService adminService;
    private final AuthenticationService authenticationService;

    public AdminController(AdminView adminView, AdminService adminService, AuthenticationService authenticationService) {
        this.adminView = adminView;
        this.adminService = adminService;
        this.authenticationService = authenticationService;

        // Adăugăm listener pentru butoanele din AdminView
        this.adminView.addSaveButtonListener(new SaveButtonListener());
        this.adminView.addViewBooksButtonListener(new ViewBooksButtonListener());
        //this.adminView.addDeleteButtonListener(new DeleteButtonListener());
        //this.adminView.addAddButtonListener(new AddButtonListener());
    }

    private void refreshTable() {
        List<UserDTO> allUsers = adminService.findAll()
                .stream()
                .map(user -> new UserDTOBuilder()
                        .setUsername(user.getUsername())
                        .setRole(user.getRoles().stream()
                                .map(Role::getRole)
                                .collect(Collectors.joining(", ")))
                        .build())
                .toList();
        adminView.getUsersObservableList().setAll(allUsers);
        adminView.getUserTableView().refresh();
    }


    private class SaveButtonListener implements EventHandler<ActionEvent>{
        public void handle (ActionEvent event){
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            Notification<Boolean> registerNotification = authenticationService.registerEmployee(username, password);

            if (registerNotification.hasErrors()) {
                adminView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                adminView.setActionTargetText("Register successful!");
            }
            refreshTable();
        }
    }

    private class ViewBooksButtonListener implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            EmployeeComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
        }
    }
}
