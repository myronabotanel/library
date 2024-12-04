package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.launcher.EmployeeComponentFactory;
import org.example.launcher.LoginComponentFactory;
import org.example.model.User;
import org.example.model.validator.Notification;
import org.example.model.validator.UserValidator;
import org.example.service.user.AuthenticationService;
import org.example.service.user.CurrentUserService;
import org.example.view.LoginView;

import java.util.EventListener;
import java.util.List;
public class LoginController
{
    private final LoginView loginView;
    private final AuthenticationService authenticationService;


    public LoginController(LoginView loginView, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else{
                loginView.setActionTargetText("LogIn Successfull!");
                CurrentUserService.setCurrentUsername(username);
                System.out.println("CurrentUser: " + username);
                EmployeeComponentFactory.getInstance(LoginComponentFactory.getComponentsForTests(), LoginComponentFactory.getStage());
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
            }
        }
    }
}