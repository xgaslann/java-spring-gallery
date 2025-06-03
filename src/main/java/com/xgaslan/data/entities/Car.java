package com.xgaslan.data.entities;

import com.xgaslan.data.entities.base.BaseNumericKeyEntity;
import jakarta.persistence.*;
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
    private String plateNumber;

    private String brand;

    private String model;

    private String color;

    private Long price;

    private Date productionDate;

    private Long mileage;

    private Long damageCost;

    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;
}
