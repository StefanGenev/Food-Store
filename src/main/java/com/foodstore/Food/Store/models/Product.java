package com.foodstore.Food.Store.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product { // Продукт в магазина

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id; // Уникален идентификатор за записа

    private String productName; // Име на продукта

    private Manufacturer manufacturer; // Фирма - производител на продукта

    @Enumerated(EnumType.STRING)
    private Unit unit; // Мерна единица (кг., литър, ...)

    private Category category; // Категория на продукта

    private double buyPrice; // Цена на зареждане - при зареждане на продукта

    private double sellPrice; // Цена на продажба - при продаване на продукта

    Product(String productName, Manufacturer manufacturer, Unit unit, Category category, double buyPrice, double sellPrice){
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.unit = unit;
        this.category = category;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

}
