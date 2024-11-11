package org.example.repository;

import org.example.model.Book;
import org.example.model.builder.BookBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

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
        String sql = "SELECT * FROM book WHERE id=" + id;

        Optional<Book> book = Optional.empty();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

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
        String newSql = "INSERT INTO book VALUES(null, \'" + book.getAuthor() + "\', \'" + book.getTitle() + "\',\'" + book.getPublishedDate() + "\' );";  // pentru a se realiza cu succes scrierea

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(newSql); //statement de ddl (update, delete, comenzile ce modifica baza de date, nu fac un simplu select. necesita cele mai mari drepturi
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Book book) {
        String newSql = "DELETE FROM book WHERE author=\'" + book.getAuthor() + "\' AND title=\'" + book.getTitle() + "\';";
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(newSql);
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
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
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
