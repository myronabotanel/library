package org.example.repository.security;

import org.example.model.Right;
import org.example.model.Role;
import org.example.model.User;

import java.util.List;

public interface RightsRolesRepository
{
    void addRole(String role);  //adaugare rol in baza de date

    void addRight(String right);  //adaugare rol

    Role findRoleByTitle(String role);  //gasire rol dupa titlu

    Role findRoleById(Long roleId);  //gasire drept dupa id

    Right findRightByTitle(String right);  //gasire drept dupa titlu

    void addRolesToUser(User user, List<Role> roles);  //adaugare roluri user

    List<Role> findRolesForUser(Long userId); //cautam dupa user id => lista roluri din db

    void addRoleRight(Long roleId, Long rightId);  //face leg dintre un rol si un drept
}