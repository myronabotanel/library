package org.example.repository;

import org.example.model.*;

import java.util.*;
//ce poate folosi service
public interface BookRepository
{
    List<Book> findAll();
    Optional<Book> findById(Long id);  //s ar putea sa avem o carte sau sa u fie gasita. scapam de null
    boolean save (Book book);
    boolean delete (Book book);
    void removeAll();  //sterge tot din baza de date


}
