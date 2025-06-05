package com.xgaslan.data.entities;

import com.xgaslan.data.entities.base.BaseUUIDKeyEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseUUIDKeyEntity {
    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @NotBlank
    @Column(name = "national_number", nullable = false, unique = true, length = 32)
    private String nationalNumber;

    private Date birthDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Account account;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;
}
