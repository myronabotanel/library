package org.example.mapper;

import org.example.model.Role;
import org.example.model.User;
import org.example.view.model.UserDTO;
import org.example.view.model.builder.UserDTOBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper
{
    // Convertește un obiect User într-un obiect UserDTO
    public static UserDTO convertUserToUserDTO(User user) {
        String roles = user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.joining(", ")); // concatenăm rolurile într-un singur string
        return new UserDTOBuilder()
                .setUsername(user.getUsername())
                .setRole(roles)  // Atribuim rolurile într-un singur string
                .build();
    }

    public static List<UserDTO> convertUserListToUserDTOList(List<User> users) {
        return users.stream().map(UserMapper::convertUserToUserDTO).collect(Collectors.toList());
    }

    // Convertește o listă de UserDTO într-o listă de Users

}
