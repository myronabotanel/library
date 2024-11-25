package org.example.mapper;

import org.example.model.Book;
import org.example.model.builder.BookBuilder;
import org.example.view.model.BookDTO;
import org.example.view.model.builder.BookDTOBuilder;

import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class BookMapper
{
    //creez met statice, sa nu fie nevoie sa creez un obiect Mapper.
    public static BookDTO convertBookToBookDTO(Book book){
        return new BookDTOBuilder().setTitle(book.getTitle()).setAuthor(book.getAuthor())
                .setPrice(book.getPrice()) // Adaugă price .setStock(book.getStock()) // Adaugă stock
                .build();
    }
    public static Book convertBookDTOToBook(BookDTO bookDTO){
        return new BookBuilder().setTitle(bookDTO.getTitle()).setAuthor(bookDTO.getAuthor()).setPublishedDate(LocalDate.of(2010, 1, 1)).setPrice(bookDTO.getPrice()).setStock(bookDTO.getStock()).build();
    }
    public static List<BookDTO> convertBookListToBookDTOList(List<Book> books){
        return books.parallelStream().map(BookMapper::convertBookToBookDTO).collect(Collectors.toList());
    }
    public static List<Book> convertBookDTOListToBookList(List<BookDTO> bookDTOS){
        return bookDTOS.parallelStream().map(BookMapper::convertBookDTOToBook).collect(Collectors.toList());
    }

}