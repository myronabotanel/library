package org.example.repository;

import org.example.model.Order;
import org.example.model.Book;
import org.example.model.Sale;
import org.example.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryMySQL implements OrderRepository
{
    private Connection connection;

    public OrderRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Order> getOrdersFromLastMonth() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM `order` WHERE order_date >= NOW() - INTERVAL 1 MONTH";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("id"),
                        resultSet.getString("book_title"),
                        resultSet.getString("seller_name"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("total_price"),
                        resultSet.getTimestamp("order_date")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
