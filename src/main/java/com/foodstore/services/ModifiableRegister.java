package com.foodstore.services;

// Интерфейс за регистри с въможност за манипулации на данните в тях
public interface ModifiableRegister<T> extends ReadableRegister<T> {
    T addRecord(T record); // Добавяне на запис
    T updateRecord(T record); // Редакция на запис
}
