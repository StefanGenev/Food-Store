package com.foodstore.repositories;

import com.foodstore.models.Load;
import com.foodstore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Клас за заявки към БД за зареждания в магазина

@Repository
public interface LoadRepository extends JpaRepository<Load, Long> {

    Optional<Load> findLoadById(Long id);

    List<Load> findByProduct(Product product); // търси зареждане на даден продукт

    void deleteLoadById(Long id);
}