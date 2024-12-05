package org.example.repository.user;

import org.example.model.Role;
import org.example.model.User;
import org.example.model.validator.Notification;

import java.util.List;

public interface UserRepository
{
    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password);

    boolean save(User user);
    boolean delete (User user);

    void removeAll();

    boolean existsByUsername(String username);
    boolean isUsersTableEmpty();
    boolean upgradeUserRole (String username, Role role);
}