package com.xgaslan.data.entities;

import com.xgaslan.data.entities.base.BaseNumericKeyEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseNumericKeyEntity {

    @Column(nullable = false, unique = true)
    @NotBlank
    private String token;

    @Column(nullable = false)
    @NotNull
    private Date expiryDate;

    @ManyToOne
    private User user;
}
