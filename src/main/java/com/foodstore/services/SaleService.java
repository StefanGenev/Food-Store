package com.foodstore.services;

import com.foodstore.exceptions.NotFoundException;
import com.foodstore.models.Sale;
import com.foodstore.repositories.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Клас за бизнес логика на продажбите

@Service
public class SaleService {
    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Sale addSale(Sale sale){
        return saleRepository.saveAndFlush(sale);
    }

    public List<Sale> findAllSales(){
        return saleRepository.findAll();
    }

    public Sale updateVinyl(Sale sale){
        return saleRepository.saveAndFlush(sale);
    }

    public Sale findVinylById(Long id){
        return saleRepository.findSaleById(id)
                .orElseThrow(() -> new NotFoundException("Не е намерена продажба с идентификатор: " + id));
    }

    @Transactional
    public void deleteSale(Long id){
        this.saleRepository.deleteSaleById(id);
    }
}
