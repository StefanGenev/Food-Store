package com.foodstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

// Базов клас за данни на стока

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class Stock {

    @Id
    @Column(name = "id", nullable = false)
    private Long id; // Идентификатор

    @OneToOne
    private Product product; // Продукт

    private Double quantity; // Количество
}