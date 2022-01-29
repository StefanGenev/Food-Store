package com.foodstore.repositories;

import com.foodstore.models.StoreData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Клас за заявки към БД за параметрите на магазина

@Repository
public interface StoreDataRepository extends JpaRepository<StoreData, Long> {
    Optional<StoreData> findStoreDataByName(String name);
}
