package com.foodstore.services;

import com.foodstore.models.StoreData;
import com.foodstore.repositories.StoreDataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Клас за бизнес логика на параметри на магазин

@Service
public class StoreDataService {
    // Параметри на магазин
    private StoreDataRepository storeDataRepository;

    public StoreDataService(StoreDataRepository storeDataRepository) {
        this.storeDataRepository = storeDataRepository;
    }

    public String getParameterValue(String parameterName){
        Optional<StoreData> storeData = this.storeDataRepository.findStoreDataByName( parameterName );
        if (storeData.isEmpty())
            return "";

        return storeData.get().getValue();
    }
}