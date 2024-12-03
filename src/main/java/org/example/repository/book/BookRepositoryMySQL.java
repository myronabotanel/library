package org.example.repository.book;

import org.example.model.Book;
import org.example.model.builder.BookBuilder;
import org.example.repository.book.BookRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BookRepositoryMySQL implements BookRepository
{
    public final Connection connection; // o data setat, sa nu se schimbe. va fi injectat din exterior

    public BookRepositoryMySQL(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public List<Book> findAll() {
        String sql = "SELECT * FROM book;";
        List<Book> books = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery(sql);

            while(resultSet.next()){  //primul apel de next ne duce la primul elem si ne zice daca mai sunt elem
                books.add(getBookfromResultSet(resultSet));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id= ?;";
        Optional<Book> book = Optional.empty();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery(sql);

            if(resultSet.next())
            {
                book = Optional.of(getBookfromResultSet(resultSet)); //=> optional va da fie o valoare mepty, daca nu va gasi o carte cu id ul nostru, fie cartea cu id ul cerut
            }
        }catch(SQLException e){
            e.printStackTrace(); //printeaza tot stack ul de erori
        }
        return book;
    }

    @Override
    public boolean save(Book book) {
        try {
            // Verificăm dacă cartea există deja
            String selectQuery = "SELECT id, stock FROM book WHERE title = ? AND author = ? AND price = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, book.getTitle());
            selectStatement.setString(2, book.getAuthor());
            selectStatement.setDouble(3, book.getPrice());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Cartea există - actualizăm stocul
                long bookId = resultSet.getLong("id");
                int existingStock = resultSet.getInt("stock");
                String updateQuery = "UPDATE book SET stock = ? WHERE id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setInt(1, existingStock + book.getStock());
                updateStatement.setLong(2, bookId);
                updateStatement.executeUpdate();
                return true;
            } else {
                // Cartea nu există - o adăugăm
                String insertQuery = "INSERT INTO book (author, title, publishedDate, price, stock) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, book.getAuthor());
                insertStatement.setString(2, book.getTitle());
                insertStatement.setDate(3, Date.valueOf(book.getPublishedDate()));
                insertStatement.setDouble(4, book.getPrice());
                insertStatement.setInt(5, book.getStock());
                insertStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean delete(Book book) {
        String newSql = "DELETE FROM book WHERE author = ? AND title = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(newSql)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void removeAll() {
        String sql = "TRUNCATE TABLE book;";  //daca folosim DELETE, id tot creste
        //nu putem conditiona TRUNCATE. delete ul da, dar, pentru o metoda de removeAll, putem folosi TRUNCATE. El necesita mai multe drepturi
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean update(Book book) {
        String sql = "UPDATE book SET author = ?, title = ?, publishedDate = ?, price = ?, stock = ? WHERE title = ? AND author = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            if (book.getPublishedDate() != null) {
                preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            } else {
                preparedStatement.setNull(3, Types.DATE);
            }
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setInt(5, book.getStock());
            preparedStatement.setString(6, book.getTitle());
            preparedStatement.setString(7, book.getAuthor());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private Book getBookfromResultSet(ResultSet resultSet) throws SQLException
    {
        return new BookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setPrice(resultSet.getDouble("price"))
                .setStock(resultSet.getInt("stock"))
                .build();
    }
}