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
@Table(name = "saled_cars", uniqueConstraints = {@UniqueConstraint(columnNames = {"gallerist_id", "car_id, customer_id"}, name = "uq_gallerist_car_customer")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaledCar extends BaseNumericKeyEntity {

    @ManyToOne
    private Gallerist gallerist;

    @ManyToOne
    private Car car;

    @ManyToOne
    private Customer customer;
}
