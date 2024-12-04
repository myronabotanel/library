package org.example.view.model.builder;

import org.example.view.model.UserDTO;

public class UserDTOBuilder {
    private UserDTO userDTO;
    public UserDTOBuilder() {
        userDTO = new UserDTO();
    }
    // Setează username-ul
    public UserDTOBuilder setUsername(String username) {
        userDTO.setUsername(username);
        return this;
    }

    // Setează rolul
    public UserDTOBuilder setRole(String role) {
        userDTO.setRole(role);
        return this;
    }

    // Returnează obiectul UserDTO complet configurat
    public UserDTO build() {
        return userDTO;
    }

}
