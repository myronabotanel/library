import org.example.model.Book;
import org.example.model.builder.BookBuilder;
import org.example.repository.BookRepositoryMySQL;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryMySQLTest {

    private static Connection connection;
    private BookRepositoryMySQL bookRepository;

    @BeforeAll
    public static void setupDatabaseConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "username", "password");
    }

    @BeforeEach
    public void setup() throws SQLException {
        // Initializează repository-ul și șterge datele existente în tabelul "book"
        bookRepository = new BookRepositoryMySQL(connection);
        connection.createStatement().execute("TRUNCATE TABLE book");
    }

    @Test
    public void testSaveAndFindById() {
        // Creează o carte și salveaz-o în baza de date
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setAuthor("Joshua Bloch");
        book.setPublishedDate(LocalDate.of(2008, 5, 8));

        boolean isSaved = bookRepository.save(book);
        assertTrue(isSaved);

        // Găsește cartea după ID (va trebui să setezi manual ID-ul în cod sau să adaptezi metoda save)
        Optional<Book> retrievedBook = bookRepository.findById(book.getId());
        assertTrue(retrievedBook.isPresent());
        assertEquals("Effective Java", retrievedBook.get().getTitle());
    }

    @Test
    public void testFindAll()
    {
        Book book1 = new BookBuilder()
                .setTitle("Mandrie si Prejudecata")
                .setAuthor("Jane Austen")
                .setPublishedDate(LocalDate.of(1813, 1, 28))
                .build();
        bookRepository.save(book1);
        Book book2 = new BookBuilder()
                .setTitle("Emma")
                .setAuthor("Jane Austen")
                .setPublishedDate(LocalDate.of(1815, 1, 28))
                .build();
        bookRepository.save(book2);

        //apelam findAll()
        List<Book> books = bookRepository.findAll();
        // Verifică dacă lista conține exact 2 cărți și că acestea au titlurile corecte
        assertEquals(2, books.size());
        assertTrue(books.stream().anyMatch(book -> book.getTitle().equals("Mandrie si Prejudecata")));
        assertTrue(books.stream().anyMatch(book -> book.getTitle().equals("Emma")));
    }
    @Test
    public void testDelete()
    {
        Book book = new BookBuilder()
                .setTitle("Mandrie si Prejudecata")
                .setAuthor("Jane Austen")
                .setPublishedDate(LocalDate.of(1813, 1, 28))
                .build();
        bookRepository.save(book);
        //stergem cartea
        boolean isDeleted = bookRepository.delete(book);
        assertTrue(isDeleted);

    }



    @AfterAll
    public static void closeDatabaseConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
