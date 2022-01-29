package com.foodstore.repositories;

import com.foodstore.models.Load;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Клас за заявки към БД за зареждания в магазина

@Repository
public interface LoadRepository extends JpaRepository<Load, Long> {

    Optional<Load> findLoadById(Long id);

    void deleteLoadById(Long id);
}