package com.foodstore.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

// Табличен клас за зареждане

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "loads")
public class Load extends Stock {

    @Column(nullable = false)
    private LocalDateTime dateOfLoading; // Дата на зареждане

}