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
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // Уникален код на фирма - производител

    private String manufacturerName; // Име на фирмата

    Manufacturer(String manufacturerName){
        this.manufacturerName = manufacturerName;
    }
}
