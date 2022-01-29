package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

// Табличен клас за продукти в магазина

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Store extends Stock{

}