package com.xgaslan.data.entities.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseNumericKeyTextEntity extends BaseTextEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
}
