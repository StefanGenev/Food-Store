package com.foodstore.services;

import com.foodstore.exceptions.NotFoundException;
import com.foodstore.models.Load;
import com.foodstore.repositories.LoadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Клас за бизнес логика на зареждания

@Service
public class LoadService {
    private final LoadRepository loadRepository;

    public LoadService(LoadRepository loadRepository) {
        this.loadRepository = loadRepository;
    }

    public Load addLoad(Load load){
        return loadRepository.saveAndFlush(load);
    }

    public List<Load> findAllLoads(){
        return loadRepository.findAll();
    }

    public Load updateLoad(Load load){
        return loadRepository.saveAndFlush(load);
    }

    public Load findLoadById(Long id){
        return loadRepository.findLoadById(id)
                .orElseThrow(() -> new NotFoundException("Не е намерено зареждане с идентификатор: " + id));
    }

    @Transactional
    public void deleteLoad(Long id){
        this.loadRepository.deleteLoadById(id);
    }
}