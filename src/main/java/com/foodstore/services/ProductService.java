package com.foodstore.services;

import com.foodstore.exceptions.InvalidDeleteException;
import com.foodstore.exceptions.NotFoundException;
import com.foodstore.models.Category;
import com.foodstore.models.Manufacturer;
import com.foodstore.models.Product;
import com.foodstore.models.StoreStock;
import com.foodstore.repositories.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements BaseCRUDServiceInterface<Product> { // клас който обслужва бизнес логика за продуктите

    private final ProductRepo productRepo;
    private final LoadService loadService;
    private final SaleService saleService;
    private final StoreStockService storeStockService;

    public ProductService(ProductRepo productRepo, LoadService loadService, SaleService saleService, StoreStockService storeStockService) {
        this.productRepo = productRepo;
        this.loadService = loadService;
        this.saleService = saleService;
        this.storeStockService = storeStockService;
    }

    @Override
    public List<Product> findAllRecords() { // взима всички продутки от базата
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
        if (this.productRepo.findById(product.getId()).isEmpty()) {
            throw new NotFoundException(String.format("Не съществува такъв продукт: %s."
                    , product.getProductName()));
        }
        return this.productRepo.saveAndFlush(product);
    }

    public Product addProduct(Product product) { // добавя продукт в базата
        if(this.storeStockService.findStoreStockByProduct(product).isEmpty()){
            StoreStock storeStock = new StoreStock(product, 0d);
            this.storeStockService.addStoreStock(storeStock);
        }
        return this.productRepo.saveAndFlush(product);
    }

    public void deleteProduct(Product product) { // изтрива продукт
        String errorMessage = "";

        if (!this.loadService.findLoadByProduct(product).isEmpty()) {
            errorMessage += String.format("Продуктът бива зареждан в магазина: %s.\n", product.getProductName());
        }
        if (!this.saleService.findSaleByProduct(product).isEmpty()) {
            errorMessage += String.format("Има продажби на този продукт: %s.\n", product.getProductName());
        }
        if (this.storeStockService.findStoreStockByProduct(product).isPresent()) {
            errorMessage += String.format("Има набор продукти от този тип в магазина: %s.\n", product.getProductName());
        }

        if (errorMessage.length() != 0) {
            throw new InvalidDeleteException(errorMessage);
        } else {
            this.productRepo.delete(product);
        }
    }
}
