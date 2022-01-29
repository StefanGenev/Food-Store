package com.foodstore.repositories;

import com.foodstore.models.StoreStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Клас за заявки към БД за стоките в магазина

@Repository
public interface StoreStockRepository extends JpaRepository<StoreStock, Long> {
    Optional<StoreStock> findStoreStockById(Long id);

    void deleteStoreStockById(Long id);
}