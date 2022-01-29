package com.foodstore.repositories;

import com.foodstore.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


// Клас за заявки към БД за продажби в магазина

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Optional<Sale> findSaleById(Long id);

    void deleteSaleById(Long id);
}