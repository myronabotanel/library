package org.example.repository.sale;

import org.example.model.Sale;
import org.example.model.User;

import java.util.List;
//va manipula înregistrările vânzărilor în baza de date. Acesta va salva și va căuta vânzările realizate.
public interface SaleRepository {
    boolean save(Sale sale);

    List<Sale> findAll();
}