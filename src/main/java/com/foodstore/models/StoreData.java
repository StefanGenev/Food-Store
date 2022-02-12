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
    // Дефиниция на имена на глобални параметри
    public static String CASH_PARAMETER = "CASH";
    public static String STORE_NAME_PARAMETER = "STORE_NAME";



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id; // Идентификатор на параметър

    @Column(nullable = false)
    private String name; // Найменование на параметър

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DataType dataType; // Тип на данна

    @Column(nullable = false)
    private String value; // Стойност

}