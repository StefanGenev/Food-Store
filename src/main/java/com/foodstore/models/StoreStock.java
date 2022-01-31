package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// Табличен клас за продукти в магазина

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "store_stocks")
public class StoreStock extends Stock{

    @OneToOne
    private Product product; // Продукт

    public StoreStock(Product product, Double quantity){
        this.setProduct(product);
        this.setQuantity(quantity);
    }

}