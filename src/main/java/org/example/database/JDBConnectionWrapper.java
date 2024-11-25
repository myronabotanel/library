package org.example.database;

import java.sql.*;

//ne vom def driver ul, username, parola, tot ce are nevoie proiectul ca sa se poata conecta la baza noastra de date
public class JDBConnectionWrapper
{
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";
    private static final String USER = "root";
    private static final String PASSWORD ="Mi10ro03!";
    private static final int TIMEOUT = 5;
    private Connection  connection;

    public JDBConnectionWrapper(String schema)
    {
        try{
            Class.forName(JDBC_DRIVER); //ca si cum pui o cartela intr un telefon
            connection = DriverManager.getConnection(DB_URL + schema + "?allowMultiQueries=true", USER, PASSWORD); //apelam nr si stabilim o conectiune
            createTables();
        }catch (ClassNotFoundException e){  //prima data punem exceptia specifica. pe urma cea mai generala
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS book(" +
                " id bigint NOT NULL AUTO_INCREMENT," +
                " author varchar(500) NOT NULL," +
                " title varchar(500) NOT NULL," +
                " publishedDate datetime DEFAULT NULL," +
                " price double NOT NULL," + // Adăugăm coloana price
                " stock bigint NOT NULL," + // Adăugăm coloana stock
                " PRIMARY KEY(id)," +
                " UNIQUE KEY id_UNIQUE(id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";

        statement.execute(sql);
    }

    public boolean testConnection() throws SQLException{
        return connection.isValid(TIMEOUT);
    }

    public Connection getConnection()
    {
        return connection;
    }
}