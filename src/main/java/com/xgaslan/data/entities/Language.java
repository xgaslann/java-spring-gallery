package com.xgaslan.data.entities;

import com.xgaslan.data.entities.base.BaseStringKeyEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "languages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Language extends BaseStringKeyEntity {

    @Column(nullable = false, unique = true)
    private String text;

    public Language(String id, String text) {
        setId(id);
        this.text = text;
    }
}
