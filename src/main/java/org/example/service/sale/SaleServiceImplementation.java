package org.example.service.sale;

import org.example.model.Sale;
import org.example.repository.sale.SaleRepository;

public class SaleServiceImplementation implements SaleService
{
    private final SaleRepository saleRepository;

    public SaleServiceImplementation(SaleRepository saleRepository){
        this.saleRepository = saleRepository;
    }
    @Override
    public boolean save(Sale sale) {
        return saleRepository.save(sale);
    }
}
