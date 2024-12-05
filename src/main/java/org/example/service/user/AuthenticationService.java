package org.example.service.user;

import org.example.model.User;
import org.example.model.validator.Notification;

public interface AuthenticationService {
    Notification<Boolean> register(String username, String password);
    Notification<Boolean> registerEmployee(String username, String password);
    Notification<User> login(String username, String password);

    boolean logout(User user);
}