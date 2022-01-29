package com.foodstore.services;

import com.foodstore.exceptions.NotFoundException;
import com.foodstore.models.Category;
import com.foodstore.models.Manufacturer;
import com.foodstore.models.Product;
import com.foodstore.repositories.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService { // клас който обслужва бизнес логика за продуктите

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() { // взима всички продутки от базата
        return this.productRepo.findAll();
    }

    public List<Product> getAllProductsByCategory(Category category) { // взима всички продукти по категория
        if (this.productRepo.findAllByCategory(category).isEmpty()) {
            throw new NotFoundException(String.format("Не съществуват такива продукти с категория: %s", category.getCategoryName()));
        }
        return this.productRepo.findAllByCategory(category);
    }

    public List<Product> getAllProductsByManufacturer(Manufacturer manufacturer) { // взима всички продукти по производител
        if (this.productRepo.findAllByManufacturer(manufacturer).isEmpty()) {
            throw new NotFoundException(String.format("Не съществуват такива продукти с производител: %s", manufacturer.getManufacturerName()));
        }
        return this.productRepo.findAllByManufacturer(manufacturer);
    }

    public Product updateProduct(Product product) { // редактира продукт
        if(this.productRepo.findById(product.getId()).isEmpty()) {
            throw new NotFoundException(String.format("Не съществува такъв продукт: %s."
                    , product.getProductName()));
        }
        return this.productRepo.saveAndFlush(product);
    }

}
