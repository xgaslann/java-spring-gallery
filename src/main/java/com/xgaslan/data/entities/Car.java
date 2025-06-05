package com.xgaslan.data.entities;

import com.xgaslan.data.entities.base.BaseNumericKeyEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "cars")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car extends BaseNumericKeyEntity {

    @NotBlank
    @Column(nullable = false, unique = true)
    private String plateNumber;

    @NotBlank
    @Column(nullable = false)
    private String brand;

    @NotBlank
    @Column(nullable = false)
    private String model;

    @NotBlank
    @Column(nullable = false)
    private String color;

    @NotBlank
    @Column(nullable = false)
    private Long price;

    @NotBlank
    @Column(nullable = false)
    private Date productionDate;

    @NotBlank
    @Column(nullable = false)
    private Long mileage;

    @NotBlank
    @Column(nullable = false)
    private Long damageCost;

    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;
}
