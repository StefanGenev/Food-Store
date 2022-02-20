package com.foodstore.repositories;

import com.foodstore.models.PeriodFilter;
import com.foodstore.models.Product;
import com.foodstore.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


// Клас за заявки към БД за продажби в магазина

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Optional<Sale> findSaleById(Long id);

    List<Sale> findSaleByProduct(Product product); // търси продажба на даден продукт

    void deleteSaleById(Long id);

    // Филтрация по период
    @Query(value = "SELECT * " +
                   "FROM SALES " +
                   "WHERE DATE_OF_SALE BETWEEN :#{#filter.dateFrom} AND :#{#filter.dateTo}\n"
                   , nativeQuery = true)
    List<Sale> findSalesForPeriod(@Param("filter")PeriodFilter periodFilter);
}