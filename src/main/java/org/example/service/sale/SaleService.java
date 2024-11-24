package org.example.service.sale;

import org.example.model.Book;
import org.example.model.Sale;
import org.example.repository.sale.SaleRepository;

import java.time.LocalDate;

public class SaleService {
    private SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public void sellBook(Book book, double price) {
        // Logica de vânzare
        if (book != null) {
            Sale sale = new Sale(book, LocalDate.now(), price);
            saleRepository.save(sale);
            // Actualizare stoc carte, de exemplu:
           // book.setInStock(book.getInStock() - 1);  // Scădem stocul cărții
            System.out.println("Sale completed: " + sale);
        }
    }
}
