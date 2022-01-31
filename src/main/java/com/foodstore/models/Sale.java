package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

// Табличен клас за продажба

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sales")
public class Sale extends Stock {

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product; // Продукт

    @Column(nullable = false)
    private LocalDateTime dateOfSale; // Дата на продажба

}