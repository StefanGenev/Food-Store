package com.foodstore.services;

import java.util.List;

// Интерфейс на сервиз за CRUD операции с бази данни
public interface BaseCRUDServiceInterface<T> {
    List<T> findAllRecords(); // Селектиране на всички записи
}
