package com.xgaslan.data.entities.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseStringKeyTextEntity extends BaseTextEntity {
    @Id
    @Column(updatable = false, nullable = false, length = 10)
    private String id;
}