package com.xgaslan.data.entities;

import com.xgaslan.data.entities.base.BaseNumericKeyEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseNumericKeyEntity {

    private String accountNo;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String iban;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;
}
