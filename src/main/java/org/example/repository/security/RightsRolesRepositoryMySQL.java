package org.example.repository.security;

import org.example.model.Right;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.example.database.Constants.Tables.RIGHT;
import static org.example.database.Constants.Tables.ROLE;
import static org.example.database.Constants.Tables.ROLE_RIGHT;
import static org.example.database.Constants.Tables.USER_ROLE;

public class RightsRolesRepositoryMySQL implements RightsRolesRepository {

    private final Connection connection;

    public RightsRolesRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addRole(String role) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO `" + ROLE + "` values (null, ?)");
            insertStatement.setString(1, role);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Override
    public void addRight(String right) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO `" + RIGHT + "` values (null, ?)");
            insertStatement.setString(1, right);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Override
    public Role findRoleByTitle(String role) {
        try {
            // Interogarea SQL cu un parametru placeholder ?
            String fetchRoleSql = "SELECT * FROM `" + ROLE + "` WHERE `role` = ?";

            // Pregătirea interogării SQL cu PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(fetchRoleSql);

            // Setarea valorii parametrului
            preparedStatement.setString(1, role);  // Setează 'role' în locul semnului de întrebare

            // Executarea interogării și obținerea rezultatului
            ResultSet roleResultSet = preparedStatement.executeQuery();

            // Verificăm dacă există un rezultat
            if (roleResultSet.next()) {
                Long roleId = roleResultSet.getLong("id");
                String roleTitle = roleResultSet.getString("role");
                return new Role(roleId, roleTitle, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public Role findRoleById(Long roleId) {
        try {
            // Interogarea SQL cu un parametru placeholder ?
            String fetchRoleSql = "SELECT * FROM `" + ROLE + "` WHERE `id` = ?";

            // Pregătirea interogării SQL cu PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(fetchRoleSql);

            // Setarea valorii parametrului
            preparedStatement.setLong(1, roleId);  // Setează roleId în locul semnului de întrebare

            // Executarea interogării și obținerea rezultatului
            ResultSet roleResultSet = preparedStatement.executeQuery();

            // Verificăm dacă există un rezultat
            if (roleResultSet.next()) {
                String roleTitle = roleResultSet.getString("role");
                return new Role(roleId, roleTitle, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public Right findRightByTitle(String right) {
        try {
            String fetchRoleSql = "SELECT * FROM `" + RIGHT + "` WHERE `right` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchRoleSql);
            preparedStatement.setString(1, right); // Corectăm de la `role` la `right`
            ResultSet rightResultSet = preparedStatement.executeQuery();
            if (rightResultSet.next()) {  // Verificăm dacă sunt rezultate
                Long rightId = rightResultSet.getLong("id");
                String rightTitle = rightResultSet.getString("right");
                return new Right(rightId, rightTitle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void addRolesToUser(User user, List<Role> roles) {
        try {
            for (Role role : roles) {
                PreparedStatement insertUserRoleStatement = connection
                        .prepareStatement("INSERT INTO `user_role` values (null, ?, ?)");
                insertUserRoleStatement.setLong(1, user.getId());
                insertUserRoleStatement.setLong(2, role.getId());
                insertUserRoleStatement.executeUpdate();
            }
        } catch (SQLException e) {

        }
    }

    @Override
    public List<Role> findRolesForUser(Long userId) {
        try {
            List<Role> roles = new ArrayList<>();
            String fetchRoleSql = "SELECT * FROM `" + USER_ROLE + "` WHERE `user_id` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(fetchRoleSql);
            preparedStatement.setLong(1, userId);  // Setăm userId în locul semnului de întrebare

            ResultSet userRoleResultSet = preparedStatement.executeQuery();
            while (userRoleResultSet.next()) {
                long roleId = userRoleResultSet.getLong("role_id");
                roles.add(findRoleById(roleId));
            }
            return roles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void addRoleRight(Long roleId, Long rightId) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO `" + ROLE_RIGHT + "` values (null, ?, ?)");
            insertStatement.setLong(1, roleId);
            insertStatement.setLong(2, rightId);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }
}