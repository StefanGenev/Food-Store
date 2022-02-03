package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

// Табличен клас за продукти в магазина

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "store_stocks")
public class StoreStock extends Stock{

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product; // Продукт

    @Column(nullable = false)
    private LocalDateTime dateTime; // Количество на продукта към дата

    public StoreStock(Product product, Double quantity){
        this.setProduct(product);
        this.setQuantity(quantity);
    }

}