package org.example.repository.sale;

import org.example.model.Book;
import org.example.model.Sale;
import org.example.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleRepositoryMySQL implements SaleRepository {

    public final Connection connection; // o data setat, sa nu se schimbe. va fi injectat din exterior

    public SaleRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Sale sale) {
        String query = "INSERT INTO `order` (book_title, seller_name, quantity, total_price, order_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sale.getBookTitle());
            preparedStatement.setString(2, sale.getSellerName());
            preparedStatement.setInt(3, sale.getQuantity());
            preparedStatement.setDouble(4, sale.getTotalPrice());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(sale.getOrderDate()));
            int rowsAffected = preparedStatement.executeUpdate(); // Aflăm câte rânduri au fost afectate
            return rowsAffected > 0; // Returnăm true dacă cel puțin un rând a fost inserat
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returnăm false în caz de eroare
        }
    }


    @Override
    public List<Sale> findAll() {
            String query = "SELECT * FROM `order`";
            List<Sale> sales = new ArrayList<>();

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Sale sale = new Sale();
                    sale.setId(resultSet.getLong("id"));
                    sale.setBookTitle(resultSet.getString("book_title"));
                    sale.setSellerName(resultSet.getString("seller_name"));
                    sale.setQuantity(resultSet.getInt("quantity"));
                    sale.setTotalPrice(resultSet.getDouble("total_price"));
                    sale.setOrderDate(resultSet.getTimestamp("order_date").toLocalDateTime());
                    sales.add(sale);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return sales;
        }
    }
