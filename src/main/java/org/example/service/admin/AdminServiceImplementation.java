package org.example.service.admin;

import org.example.model.Role;
import org.example.model.User;
import org.example.model.builder.UserBuilder;
import org.example.model.validator.Notification;
import org.example.repository.security.RightsRolesRepository;
import org.example.repository.user.UserRepository;

import java.beans.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.database.Constants.Tables.USER;

public class AdminServiceImplementation implements AdminService
{
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AdminServiceImplementation(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> AddEmployee(String username, String password) {
        Notification<Boolean> addEmployeeNotification = new Notification<>();

        // Verificăm dacă username-ul există deja
        if(userRepository.existsByUsername(username)) {
            addEmployeeNotification.addError("Username already exists!");
            addEmployeeNotification.setResult(Boolean.FALSE);
            return addEmployeeNotification;
        }

        // Cream un user
        User user = new UserBuilder().setUsername(username).setPassword(password).build();

        // Atribuim rolul de EMPLOYEE (2 este presupus ID-ul rolului de employee)
        Role employeeRole = rightsRolesRepository.findRoleById(2L);
        if (employeeRole == null) {
            addEmployeeNotification.addError("Role not found!");
            addEmployeeNotification.setResult(Boolean.FALSE);
            return addEmployeeNotification;
        }

        // Adăugăm rolul utilizatorului
        //rightsRolesRepository.addRolesToUser(user, Collections.singletonList(employeeRole));

        // Salvăm utilizatorul
        boolean isUserSaved = userRepository.save(user);
        if (!isUserSaved) {
            addEmployeeNotification.addError("Failed to save the user!");
            addEmployeeNotification.setResult(Boolean.FALSE);
        } else {
            // După ce utilizatorul a fost salvat, ID-ul său va fi setat
            // Adăugăm rolul utilizatorului
            rightsRolesRepository.addRolesToUser(user, Collections.singletonList(employeeRole));
            addEmployeeNotification.setResult(Boolean.TRUE);

        }

        return addEmployeeNotification;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean delete(User user) {
        return userRepository.delete(user);
    }

    @Override
    public boolean updateUserRole(String username, Role role) {
        return userRepository.upgradeUserRole(username, role);
    }
}
