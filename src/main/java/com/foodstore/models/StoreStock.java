package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

// Табличен клас за продукти в магазина

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "store_stocks", indexes = {
    @Index(name = "UX_PRODUCT_AVAILABILITY_DATE", columnList = "product_id, availabilityDate", unique = true)
})
public class StoreStock extends Stock{

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="product_id", nullable=false)
    private Product product; // Продукт

    @Column(nullable = false)
    private LocalDate availabilityDate; // Количество на продукта към дата

    public StoreStock(Product product, Double quantity){
        this.setProduct(product);
        this.setQuantity(quantity);
    }

    public StoreStock(Product product, LocalDate localDate, Double quantity, long id) {
        this.setProduct(product);
        this.setQuantity(quantity);
        this.availabilityDate = localDate;
        this.id = id;
    }
}