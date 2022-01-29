package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class StoreData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id; // Идентификатор на параметър

    @Column(nullable = false)
    private String name; // Найменование на параметър

    @Column(nullable = false)
    private DataType dataType; // Тип на данна

    @Column(nullable = false)
    private String value; // Стойност

}