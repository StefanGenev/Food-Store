package com.foodstore.services;

import java.util.List;

// Интерфейс за регистри с възможност за четене на данните в тях
public interface ReadableRegister<T> {
    List<T> findAllRecords(); // Селектиране на всички записи
}
