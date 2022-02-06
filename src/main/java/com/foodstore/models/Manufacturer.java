package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// Табличен клас за пройзводител

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "manufacturers")
public class Manufacturer extends NameableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id; // Уникален код на фирма - производител

    @Column(nullable = false)
    private String manufacturerName; // Име на фирмата

    public Manufacturer(String manufacturerName){
        this.manufacturerName = manufacturerName;
    }

    @Override
    public String getName() {
        return getManufacturerName();
    }
}
