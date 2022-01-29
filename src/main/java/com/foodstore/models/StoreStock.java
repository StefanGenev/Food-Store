package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

// Табличен клас за продукти в магазина

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "store_stocks")
public class StoreStock extends Stock{

}