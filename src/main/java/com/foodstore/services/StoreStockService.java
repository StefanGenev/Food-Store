package com.foodstore.services;

import com.foodstore.exceptions.NotFoundException;
import com.foodstore.models.*;
import com.foodstore.repositories.StoreDataRepository;
import com.foodstore.repositories.StoreStockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Клас за бизнес логика на магазин

@Service
public class StoreStockService implements ReadableRegister<StoreStock> {
    public static final String CASH_PARAMETER_NAME = "CASH";

    private final StoreStockRepository storeStockRepository; // Магазин - стоки
    private final StoreDataRepository storeDataRepository; // Параметри на магазин

    public StoreStockService(StoreStockRepository storeStockRepository, StoreDataRepository storeDataRepository) {
        this.storeStockRepository = storeStockRepository;
        this.storeDataRepository = storeDataRepository;
    }

    @Override
    public List<StoreStock> findAllRecords() {
        return storeStockRepository.findAll();
    }

    public StoreStock addRecord(StoreStock record) {
        return storeStockRepository.saveAndFlush(record);
    }

    public Optional<StoreStock> findStoreStockByProduct(Product product){
        return this.storeStockRepository.findStoreStockByProduct(product);
    }

    public StoreStock updateStoreStock(StoreStock storeStock){
        return storeStockRepository.saveAndFlush(storeStock);
    }

    public StoreStock findLoadById(Long id){
        return storeStockRepository.findStoreStockById(id)
                .orElseThrow(() -> new NotFoundException("Не е намерена стока с идентификатор: " + id));
    }

    public boolean updateCashRegister( Sale sale ){
        Optional<StoreData> storeData = this.storeDataRepository.findStoreDataByName( CASH_PARAMETER_NAME );
        if (storeData.isEmpty())
            return false;

        double cash = Double.parseDouble(storeData.get().getValue());
        cash += sale.getQuantity()* sale.getProduct().getSellPrice();

        storeData.get().setValue(Double.toString(cash));

        this.storeDataRepository.saveAndFlush(storeData.get());

        return true;
    }

    public boolean updateCashRegister( Load load ){
        Optional<StoreData> storeData = this.storeDataRepository.findStoreDataByName( CASH_PARAMETER_NAME );
        if (storeData.isEmpty())
            return false;

        double cash = Double.parseDouble(storeData.get().getValue());
        cash += load.getQuantity() * load.getProduct().getLoadPrice();

        storeData.get().setValue(Double.toString(cash));

        this.storeDataRepository.saveAndFlush(storeData.get());

        return true;
    }
}
