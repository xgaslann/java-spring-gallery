package com.xgaslan.data.entities;

import com.xgaslan.data.entities.base.BaseNumericKeyEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseNumericKeyEntity {
    @Column(nullable = false)
    @NotBlank
    private String street;

    @Column(nullable = false)
    @NotBlank
    private String neighborhood;

    @ManyToOne(optional = false)
    private City city;

    @ManyToOne(optional = false)
    private Country country;
}
