import org.example.model.Book;
import org.example.model.builder.BookBuilder;
import org.example.repository.BookRepository;
import org.example.repository.BookRepositoryMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookRepositoryMockTest
{
    private static BookRepository bookRepository;

    @BeforeAll //o singura data pe clasa de test
    public static void setup() {bookRepository = new BookRepositoryMock();}

    @Test
    public void findAll(){
        List<Book> books =  bookRepository.findAll();
        assertEquals(0, books.size());
    }

    @Test
    public void findById(){
        final Optional<Book> book = bookRepository.findById(1L);
        assertTrue(book.isEmpty());
    }

    @Test
    public void save(){
        assertTrue(bookRepository.save(new BookBuilder().setTitle("Ion").setAuthor("Liviu Rebreanu").setPublishedDate(LocalDate.of(1900, 10, 2)).build()));
    }
}
