package com.xgaslan.data.entities;

import com.xgaslan.data.entities.base.BaseStringKeyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "currencies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Currency extends BaseStringKeyEntity {

    @Column(nullable = false, unique = true)
    private String text;

    public Currency(String id, String text) {
        setId(id);
        this.text = text;
    }

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;
}