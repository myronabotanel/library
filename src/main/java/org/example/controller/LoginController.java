package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.model.User;
import org.example.model.validator.UserValidator;
import org.example.service.user.AuthenticationService;
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

            User user =authenticationService.login(username, password);
            if(user == null){
                loginView.setActionTargetText("Invalid Username or password");
            }else{
                loginView.setActionTargetText("Login successful!");
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            User user =authenticationService.login(username, password);
            UserValidator userValidator = new UserValidator(user);
            userValidator.validate();
            final List<String> errors = userValidator.getErrors();
            if(errors.isEmpty()){
                if(authenticationService.register(username, password)){
                    loginView.setActionTargetText("Register successfull");
                }else{
                    loginView.setActionTargetText("Register not successfull");
                }
            }else{
                loginView.setActionTargetText(userValidator.getFormattedErrors());
            }
        }
    }
}
