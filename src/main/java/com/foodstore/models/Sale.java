package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

// Табличен клас за продажба

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sales")
public class Sale extends Stock {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="product_id", nullable=false)
    private Product product; // Продукт

    @Column(nullable = false)
    private LocalDate dateOfSale; // Дата на продажба

}