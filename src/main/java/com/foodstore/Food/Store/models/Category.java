package com.foodstore.Food.Store.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Category { // Категория за продукти

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id; // Уникален идентификатор за записа

    private String categoryName; // Име на категорията

    Category(String categoryName) { // Конструктор по име на категория
        this.id = 0L;
        this.categoryName = categoryName;
    }

}
