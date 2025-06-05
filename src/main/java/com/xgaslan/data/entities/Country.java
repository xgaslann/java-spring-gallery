package com.xgaslan.data.entities;

import com.xgaslan.data.entities.base.BaseNumericKeyEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country extends BaseNumericKeyEntity {
    @NotBlank
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "country")
    private List<City> cities;

    public Country(Long id, String name) {
        setId(id);
        this.name = name;
    }
}
