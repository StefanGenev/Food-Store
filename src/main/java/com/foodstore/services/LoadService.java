package com.foodstore.services;

import com.foodstore.exceptions.NotFoundException;
import com.foodstore.models.Load;
import com.foodstore.models.Product;
import com.foodstore.repositories.LoadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Клас за бизнес логика на зареждания

@Service
public class LoadService implements ModifiableRegister<Load> {
    private final LoadRepository loadRepository;

    public LoadService(LoadRepository loadRepository) {
        this.loadRepository = loadRepository;
    }

    @Override
    public List<Load> findAllRecords(){
        return loadRepository.findAll();
    }

    public List<Load> findLoadByProduct(Product product){ // търси зареждане на даден продукт
        return this.loadRepository.findByProduct(product);
    }

    public Load findLoadById(Long id){
        return loadRepository.findLoadById(id)
                .orElseThrow(() -> new NotFoundException("Не е намерено зареждане с идентификатор: " + id));
    }

    public void addLoad(Load load){
        load.setId(this.loadRepository.getCurrentId() + 1);
        this.loadRepository.saveAndFlush(load);
    }

    @Override
    public Load addRecord(Load record) {
        return null;
    }

    @Override
    public Load updateRecord(Load record) {
        return null;
    }

    @Override
    public void deleteRecord(Load record) {

    }
}