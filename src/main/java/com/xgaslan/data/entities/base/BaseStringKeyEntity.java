package com.xgaslan.data.entities.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseStringKeyEntity extends BaseEntity {
    @Id
    @Column(updatable = false, nullable = false, length = 10)
    private String id;
}
