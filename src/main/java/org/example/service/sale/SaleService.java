package org.example.service.sale;

import org.example.model.Book;
import org.example.model.Sale;
import org.example.repository.sale.SaleRepository;

import java.time.LocalDate;

public interface SaleService {
    boolean save (Sale sale);

}
