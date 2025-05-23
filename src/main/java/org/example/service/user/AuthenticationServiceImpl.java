package org.example.service.user;

import org.example.model.Role;
import org.example.model.User;
import org.example.model.builder.UserBuilder;
import org.example.model.validator.Notification;
import org.example.model.validator.UserValidator;
import org.example.repository.security.RightsRolesRepository;
import org.example.repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

import static org.example.database.Constants.Roles.CUSTOMER;
import static org.example.database.Constants.Roles.EMPLOYEE;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password) {
        // Verifică dacă username-ul este deja folosit
        if (userRepository.existsByUsername(username)) {
            Notification<Boolean> userRegisterNotification = new Notification<>();
            userRegisterNotification.addError("Username already used!");
            userRegisterNotification.setResult(Boolean.FALSE);
            return userRegisterNotification; // Întoarce notificarea cu mesaj de eroare
        }
        boolean isFirstUser = userRepository.isUsersTableEmpty();

        Role role = isFirstUser ? rightsRolesRepository.findRoleByTitle("ADMINISTRATOR") : rightsRolesRepository.findRoleByTitle(CUSTOMER);


        Role customerRole = rightsRolesRepository.findRoleByTitle(CUSTOMER);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(role))
                .build();

        UserValidator userValidator = new UserValidator(user);

        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid){
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(hashPassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }

        return userRegisterNotification;
    }

    @Override
    public Notification<Boolean> registerEmployee(String username, String password) {
        // Verifică dacă username-ul este deja folosit
        if (userRepository.existsByUsername(username)) {
            Notification<Boolean> userRegisterNotification = new Notification<>();
            userRegisterNotification.addError("Username already used!");
            userRegisterNotification.setResult(Boolean.FALSE);
            return userRegisterNotification; // Întoarce notificarea cu mesaj de eroare
        }


        Role role = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(role))
                .build();

        UserValidator userValidator = new UserValidator(user);

        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid){
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(hashPassword(password));
            userRegisterNotification.setResult(userRepository.save(user));
        }

        return userRegisterNotification;
    }

    @Override
    public Notification<User> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, hashPassword(password));
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    private String hashPassword(String password) {
        try {
            // Sercured Hash Algorithm - 256
            // 1 byte = 8 biți
            // 1 byte = 1 char
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}