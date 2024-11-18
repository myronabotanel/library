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

    @Override
    public boolean save(Book book) {
        String newSql = "INSERT INTO book (author, title, publishedDate) VALUES (?, ?, ?);";  // pentru a se realiza cu succes scrierea

        try(PreparedStatement preparedStatement = connection.prepareStatement(newSql)) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
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
