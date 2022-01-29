package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category { // Категория за продукти


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // Уникален идентификатор за записа

    @Column(nullable = false)
    private String categoryName; // Име на категорията

    Category(String categoryName) { // Конструктор по име на категория
        this.id = 0L;
        this.categoryName = categoryName;
    }

}
