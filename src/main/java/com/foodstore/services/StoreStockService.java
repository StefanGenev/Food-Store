package com.foodstore.services;

import com.foodstore.exceptions.NotFoundException;
import com.foodstore.models.*;
import com.foodstore.repositories.ProductRepo;
import com.foodstore.repositories.StoreDataRepository;
import com.foodstore.repositories.StoreStockRepository;
import javafx.util.converter.DoubleStringConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Клас за бизнес логика на магазин

@Service
public class StoreStockService implements ModifiableRegister<StoreStock> {
    private final StoreStockRepository storeStockRepository; // Магазин - стоки
    private final StoreDataRepository storeDataRepository; // Параметри на магазин
    private final ProductRepo productRepo; // сервиз за продукти
    private final LocalDate CURRENT_DATE = LocalDate.now();
    private final StoreDataService storeDataService;

    public StoreStockService(StoreStockRepository storeStockRepository, StoreDataRepository storeDataRepository, ProductRepo productRepo, StoreDataService storeDataService) {
        this.storeStockRepository = storeStockRepository;
        this.storeDataRepository = storeDataRepository;
        this.productRepo = productRepo;
        this.storeDataService = storeDataService;
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

    public void updateStoreStockByProductAndAvailabilityDate(StoreStock storeStock, boolean isLoad) {
        Optional<StoreStock> currentDateProduct =
                this.storeStockRepository.getStoreStockByProductAndAvailabilityDate(storeStock.getProduct(), storeStock.getAvailabilityDate());

        // ако няма продукт към днешна дата
        if (currentDateProduct.isEmpty()) {
            currentDateProduct = this.storeStockRepository.getClosestToGivenDateStoreStock(storeStock.getProduct(), storeStock.getAvailabilityDate());
            if (currentDateProduct.isEmpty()) {
                StoreStock toAdd = new StoreStock(storeStock.getProduct(), storeStock.getAvailabilityDate()
                        , storeStock.getQuantity(), this.storeStockRepository.getMaxId() + 1);
                this.storeStockRepository.saveAndFlush(toAdd);
                currentDateProduct = Optional.of(toAdd);
            } else {
                // нека да го добавим
                currentDateProduct.get().setProduct(storeStock.getProduct());
                currentDateProduct.get().setAvailabilityDate(storeStock.getAvailabilityDate());
                currentDateProduct.get().setQuantity(storeStock.getQuantity());
                currentDateProduct.get().setId(this.storeStockRepository.getMaxId());
                this.storeStockRepository.saveAndFlush(currentDateProduct.get());
            }
        }

        // продукт към дата, който ще редактираме
        Optional<StoreStock> storeStockToUpdate = this.storeStockRepository.getStoreStockByProductAndAvailabilityDate(
                currentDateProduct.get().getProduct()
                , currentDateProduct.get().getAvailabilityDate());

        // ако зареждаме, добавяме количеството, иначе вадим от него
        if (isLoad) {
            storeStockToUpdate.get().setQuantity(currentDateProduct.get().getQuantity() + storeStock.getQuantity());
        } else {
            storeStockToUpdate.get().setQuantity(currentDateProduct.get().getQuantity() - storeStock.getQuantity());
        }

        // накрая редактираме storestock-а
        this.storeStockRepository.saveAndFlush(storeStockToUpdate.get());
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
        cash -= load.getQuantity() * load.getProduct().getLoadPrice();

        storeData.get().setValue(Double.toString(cash));

        this.storeDataRepository.saveAndFlush(storeData.get());

        return true;
    }

    // актуализира наличността към днешна дата
    private void updateStoreStocks() {
        List<Product> allProducts = this.productRepo.findAll(); // всички продукти в базата

        // текущ продукт
        for (Product currentProduct : allProducts) { // търсим наличност към днешна дата за всеки продукт в базата
            // ако няма наличност към днешна дата
            if (this.storeStockRepository.findStoreStockByProductAndAvailabilityDate(currentProduct, CURRENT_DATE).isEmpty()) {
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

    public List<StoreStock> takeCurrentDateAvailability() {
        this.updateStoreStocks(); // актуализира наличността към днешна дата
        return this.storeStockRepository.findStoreStockForCurrentDate();
    }

    public Double getCash() {
        DoubleStringConverter doubleStringConverter = new DoubleStringConverter();
        return doubleStringConverter.fromString(
                this.storeDataService.getParameterValue(StoreData.CASH_PARAMETER));
    }
}
