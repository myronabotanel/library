package org.example.repository.user;

import org.example.model.User;

import java.util.List;

public interface UserRepository
{
    List<User> findAll();

    User findByUsernameAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    boolean existsByUsername(String username);
}
