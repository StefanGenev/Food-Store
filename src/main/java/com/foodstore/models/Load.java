package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

// Табличен клас за зареждане

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "loads")
public class Load extends Stock {

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="product_id", nullable=false)
    private Product product; // Продукт

    @Column(nullable = false)
    private LocalDate dateOfLoading; // Дата на зареждане

}