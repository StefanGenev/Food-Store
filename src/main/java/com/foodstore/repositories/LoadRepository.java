package com.foodstore.repositories;

import com.foodstore.models.Load;
import com.foodstore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Клас за заявки към БД за зареждания в магазина

@Repository
public interface LoadRepository extends JpaRepository<Load, Long> {

    Optional<Load> findLoadById(Long id);

    List<Load> findByProduct(Product product); // търси зареждане на даден продукт

    void deleteLoadById(Long id);

    // Взима докъдето е стигнал брояча на уникалния ключ на таблицата със зарежданията
    @Query(value = "SELECT CASE WHEN MAX(ID) IS NULL THEN 0 ELSE MAX(ID) END FROM LOADS", nativeQuery = true)
    long getCurrentId();
}