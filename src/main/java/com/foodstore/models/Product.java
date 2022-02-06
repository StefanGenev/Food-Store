package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends NameableEntity { // Продукт в магазина

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // Уникален идентификатор за записа

    private String productName; // Име на продукта

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="manufacturer_id", nullable=false)
    private Manufacturer manufacturer; // Фирма - производител на продукта

    @Enumerated(EnumType.STRING)
    private Unit unit; // Мерна единица (кг., литър, ...)

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="category_id", nullable=false)
    private Category category; // Категория на продукта

    @Column(nullable = false)
    private double loadPrice; // Цена на зареждане - при зареждане на продукта

    @Column(nullable = false)
    private double sellPrice; // Цена на продажба - при продаване на продукта

    Product(String productName, Manufacturer manufacturer, Unit unit, Category category, double loadPrice, double sellPrice){
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.unit = unit;
        this.category = category;
        this.loadPrice = loadPrice;
        this.sellPrice = sellPrice;
    }

    @Override
    public String getName() {
        return getProductName();
    }
}
