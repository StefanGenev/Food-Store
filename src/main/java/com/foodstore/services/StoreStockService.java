package com.foodstore.services;

import com.foodstore.exceptions.NotFoundException;
import com.foodstore.models.*;
import com.foodstore.repositories.ProductRepo;
import com.foodstore.repositories.StoreDataRepository;
import com.foodstore.repositories.StoreStockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Клас за бизнес логика на магазин

@Service
public class StoreStockService implements ModifiableRegister<StoreStock> {
    private final StoreStockRepository storeStockRepository; // Магазин - стоки
    private final StoreDataRepository storeDataRepository; // Параметри на магазин
    private final ProductRepo productRepo; // сервиз за продукти
    private final LocalDate CURRENT_DATE = LocalDate.now();

    public StoreStockService(StoreStockRepository storeStockRepository, StoreDataRepository storeDataRepository, ProductRepo productRepo) {
        this.storeStockRepository = storeStockRepository;
        this.storeDataRepository = storeDataRepository;
        this.productRepo = productRepo;
    }

    public StoreStock addStoreStock(StoreStock storeStock) {
        return storeStockRepository.saveAndFlush(storeStock);
    }

    public Optional<StoreStock> findStoreStockByProduct(Product product) {
        return this.storeStockRepository.findStoreStockByProduct(product);
    }

    public List<StoreStock> findAllStoreStocks() {
        return storeStockRepository.findAll();
    }

    public StoreStock updateStoreStock(StoreStock storeStock) {
        return storeStockRepository.saveAndFlush(storeStock);
    }

    public StoreStock findLoadById(Long id) {
        return storeStockRepository.findStoreStockById(id)
                .orElseThrow(() -> new NotFoundException("Не е намерена стока с идентификатор: " + id));
    }

    @Transactional
    public void deleteLoad(Long id) {
        this.storeStockRepository.deleteStoreStockById(id);
    }

    public boolean updateCashRegister(Sale sale) {
        Optional<StoreData> storeData = this.storeDataRepository.findStoreDataByName(StoreData.CASH_PARAMETER);
        if (storeData.isEmpty())
            return false;

        double cash = Double.parseDouble(storeData.get().getValue());
        cash += sale.getQuantity() * sale.getProduct().getSellPrice();

        storeData.get().setValue(Double.toString(cash));

        this.storeDataRepository.saveAndFlush(storeData.get());

        return true;
    }

    public boolean updateCashRegister(Load load) {
        Optional<StoreData> storeData = this.storeDataRepository.findStoreDataByName(StoreData.CASH_PARAMETER);
        if (storeData.isEmpty())
            return false;

        double cash = Double.parseDouble(storeData.get().getValue());
        cash += load.getQuantity() * load.getProduct().getLoadPrice();

        storeData.get().setValue(Double.toString(cash));

        this.storeDataRepository.saveAndFlush(storeData.get());

        return true;
    }

    // Търси продукт в лист от StoreStocks, по днешна дата
    private boolean searchProductInStoreStocks(long productId, List<StoreStock> storeStocksList) {
        for (int j = 0; j < storeStocksList.size(); j++) { // въртим записите в масива
            StoreStock currentSS = storeStocksList.get(j);
            if (currentSS.getProduct().getId().equals(productId)) // търсим по ID на продукт
                if (currentSS.getAvailabilityDate().equals(LocalDate.now())) { // и днешна дата
                    return true; // има наличност към днешна дата, излизаме
                }
        }
        return false; // ако стигнем до тук, няма наличност към днешна дата
    }

    // актуализира наличността към днешна дата
    private void updateStoreStocks() {
        List<Product> allProducts = this.productRepo.findAll(); // всички продукти в базата

        for (int i = 0; i < allProducts.size(); i++) { // търсим наличност към днешна дата за всеки продукт в базата
            Product currentProduct = allProducts.get(i); // текущ продукт
            // ако няма наличност към днешна дата
            if (this.storeStockRepository.findStoreStockByProductAndAvailabilityDate(currentProduct, CURRENT_DATE).isEmpty())
            {
                // ще добавим наличност към днешна дата
                StoreStock storeStockCurrentDate = new StoreStock();
                storeStockCurrentDate.setProduct(currentProduct);
                storeStockCurrentDate.setAvailabilityDate(CURRENT_DATE);
                // взимаме количеството за близката до актуалната дата в базата
                Optional<StoreStock> ssQuantity = this.storeStockRepository.findStoreStockByProductAndMaxAvailabilityDate(currentProduct);
                storeStockCurrentDate.setQuantity(ssQuantity.isPresent() ? ssQuantity.get().getQuantity() : 0);
                // взимаме си ново ID
                storeStockCurrentDate.setId(this.storeStockRepository.getMaxId() + 1);

                this.storeStockRepository.saveAndFlush(storeStockCurrentDate);
            }
        }
    }

    @Override
    public List<StoreStock> findAllRecords() { // актуализира наличността към днешна дата и връща всички записи
        this.updateStoreStocks(); // актуализира наличността към днешна дата
        return this.findAllStoreStocks(); // връща всички записи от таблицата
    }

    @Override
    public StoreStock addRecord(StoreStock record) {
        return null;
    }

    @Override
    public StoreStock updateRecord(StoreStock record) {
        return null;
    }

    @Override
    public void deleteRecord(StoreStock record) {

    }

    public List<StoreStock> doFilterSpr(StoreStock filter) {
        return this.storeStockRepository.findStoreStockByCategoryForDate(filter);
    }
}
