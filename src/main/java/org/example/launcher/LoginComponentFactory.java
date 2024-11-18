package org.example.launcher;

import org.example.controller.LoginController;
import org.example.database.DatabaseConnectionFactory;
import org.example.repository.book.BookRepositoryMySQL;
import org.example.repository.security.RightsRolesRepository;
import org.example.repository.security.RightsRolesRepositoryMySQL;
import org.example.repository.user.UserRepository;
import org.example.repository.user.UserRepositoryMySQL;
import org.example.service.user.AuthenticationService;
import org.example.service.user.AuthenticationServiceMySQL;
import org.example.view.LoginView;


import javafx.stage.Stage;
import java.sql.Connection;
import org.example.controller.*;
import org.example.database.*;


public class LoginComponentFactory {
    private final LoginView loginView;
    private final LoginController loginController;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final BookRepositoryMySQL bookRepository;
    private static LoginComponentFactory instance;
    private static Boolean componentsForTests;
    private static Stage stage;

    public static LoginComponentFactory getInstance(Boolean aComponentsForTests, Stage aStage) {
        if (instance == null) {
            componentsForTests = aComponentsForTests;
            stage = aStage;
            instance = new LoginComponentFactory(componentsForTests, stage);
        }

        return instance;
    }

    public LoginComponentFactory(Boolean componentsForTests, Stage stage){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
        this.loginView = new LoginView(stage);
        this.loginController = new LoginController(loginView, authenticationService);
        this.bookRepository = new BookRepositoryMySQL(connection);
    }

    public static Stage getStage(){
        return stage;
    }

    public static Boolean getComponentsForTests(){
        return componentsForTests;
    }

    public AuthenticationService getAuthenticationService(){
        return authenticationService;
    }

    public UserRepository getUserRepository(){
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository(){
        return rightsRolesRepository;
    }

    public LoginView getLoginView(){
        return loginView;
    }

    public BookRepositoryMySQL getBookRepository(){
        return bookRepository;
    }

    public LoginController getLoginController(){
        return loginController;
    }

}