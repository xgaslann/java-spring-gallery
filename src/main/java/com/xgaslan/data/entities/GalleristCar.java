package com.xgaslan.data.entities;

import com.xgaslan.data.entities.base.BaseNumericKeyEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gallerist_cars", uniqueConstraints = {@UniqueConstraint(columnNames = {"gallerist_id", "car_id"}, name = "uq_gallerist_car")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GalleristCar extends BaseNumericKeyEntity {
    @ManyToOne
    private Gallerist gallerist;

    @ManyToOne
    private Car car;
}
