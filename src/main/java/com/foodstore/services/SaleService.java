package com.foodstore.services;

import com.foodstore.exceptions.NotFoundException;
import com.foodstore.models.Product;
import com.foodstore.models.Sale;
import com.foodstore.repositories.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Клас за бизнес логика на продажбите

@Service
public class SaleService implements ReadableRegister<Sale> {
    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> findAllRecords() {
        return saleRepository.findAll();
    }

    public List<Sale> findSaleByProduct(Product product) { // търси продажба на даден продукт
        return this.saleRepository.findSaleByProduct(product);
    }

    public Sale findSaleById(Long id){
        return saleRepository.findSaleById(id)
                .orElseThrow(() -> new NotFoundException("Не е намерена продажба с идентификатор: " + id));
    }
}
