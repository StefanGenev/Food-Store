package com.foodstore.models;

// Мерна единица
public enum Unit {
    KILOGRAM("Килограм"),
    LITRE("Литър"),
    COUNT("Брой"),
    GRAM("Грам");


    // Име за изобразяване
    private String name;

    public String getName() {
        return name;
    }

    Unit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
