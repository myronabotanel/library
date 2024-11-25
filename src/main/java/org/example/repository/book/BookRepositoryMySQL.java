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
            e.printStackTrace();
        }
        return book;
    }
    public Optional<Book> findByTitleAndAuthor(String title, String author) {
        String sql = "SELECT * FROM book WHERE title = ? AND author = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Book book = new BookBuilder()
                        .setTitle(resultSet.getString("title"))
                        .setAuthor(resultSet.getString("author"))
                        .setPrice(resultSet.getDouble("price"))
                        .setStock(resultSet.getLong("stock"))
                        .build();
                return Optional.of(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public boolean save(Book book) {
        String sql = "INSERT INTO book (author, title, publishedDate, price, stock) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println("Saving book: " + book); // Log pentru carte

            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());

            // publishedDate este null
            if (book.getPublishedDate() != null) {
                preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            } else {
                preparedStatement.setNull(3, Types.DATE);
            }

            preparedStatement.setLong(4, book.getStock());
            preparedStatement.setDouble(5, book.getPrice());

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted); // Log pentru rânduri inserate

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        book.setId(generatedKeys.getLong(1));
                        System.out.println("Generated ID: " + book.getId()); // Log pentru ID generat
                    }
                }
            }

            System.out.println("Saving book: " + book.getTitle() + ", " + book.getAuthor() + ", " +
                    book.getPublishedDate() + ", " + book.getStock() + ", " + book.getPrice());

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error saving book: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            return false;
        }
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
    public boolean updateStock(long id, int newStock) {
        String sql = "UPDATE book SET stock = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newStock);
            preparedStatement.setLong(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating stock: " + e.getMessage());
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
                .build();
    }

}