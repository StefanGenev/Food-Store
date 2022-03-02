package com.foodstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

// Базов клас за данни на стока

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class Stock extends NameableEntity{

    @Id
    @Column(name = "id", nullable = false)
    protected Long id; // Идентификатор

    @Column(nullable = false)
    protected Double quantity; // Количество

    @Override
    public String getName() {
        return "";
    }
}
