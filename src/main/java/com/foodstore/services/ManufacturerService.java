package com.foodstore.services;

import com.foodstore.exceptions.InvalidDeleteException;
import com.foodstore.exceptions.NotFoundException;
import com.foodstore.models.Manufacturer;
import com.foodstore.repositories.ManufacturerRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerService { // клас който обслужва бизнес логика за фирмите

    private final ManufacturerRepo manufacturerRepo;
    private final ProductService productService;

    public ManufacturerService(ManufacturerRepo manufacturerRepo, ProductService productService) {
        this.manufacturerRepo = manufacturerRepo;
        this.productService = productService;
    }

    public List<Manufacturer> getAllManufacturers(){ // взима всички фирми от базата
        return this.manufacturerRepo.findAll();
    }

    public Optional<Manufacturer> getManufacturerById(long id){ // взима фирма/производител по id
        if(this.manufacturerRepo.findById(id).isEmpty()) {
            throw new NotFoundException(String.format("Не съществува такава фирма/производител id: %d.", id));
        }
        return this.manufacturerRepo.findById(id);
    }

    public Manufacturer updateManufacturer(Manufacturer manufacturer){ // Редактира фирма/производител
        if(this.manufacturerRepo.findById(manufacturer.getId()).isEmpty()) {
            throw new NotFoundException(String.format("Не съществува такава фирма/производител: %s."
                    , manufacturer.getManufacturerName()));
        }
        return this.manufacturerRepo.saveAndFlush(manufacturer);
    }

    public Manufacturer addManufacturer(Manufacturer manufacturer){ // Добавяне на фирма/производител
        return this.manufacturerRepo.saveAndFlush(manufacturer);
    }

    public void deleteCategory(Manufacturer manufacturer) { // изтрива фирма/производител ако нямат продукти
        if (this.productService.getAllProductsByManufacturer(manufacturer).isEmpty()) {
            this.manufacturerRepo.delete(manufacturer);
        } else {
            throw new InvalidDeleteException(String.format(
                    "Производител: %s не може да бъде изтрит. Фирмата има съществуващи продукти в базата."
                    , manufacturer.getManufacturerName()));
        }
    }

}