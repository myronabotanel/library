package org.example.service.admin;

import org.example.model.User;
import org.example.model.validator.Notification;

import java.util.List;

public interface AdminService {
    Notification<Boolean> AddEmployee (String username, String password);
    public List<User> findAll();
}
