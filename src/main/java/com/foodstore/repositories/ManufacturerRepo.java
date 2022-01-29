package com.foodstore.repositories;

import com.foodstore.models.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// интерфейс за фирмите за връзка с базата и изпълнение на заявки към нея
@Repository
public interface ManufacturerRepo extends JpaRepository<Manufacturer, Long> {



}