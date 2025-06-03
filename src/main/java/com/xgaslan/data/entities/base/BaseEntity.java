package com.xgaslan.data.entities.base;

import com.xgaslan.data.constants.AuditConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(nullable = false, updatable = false)
    private UUID createdBy;

    @LastModifiedDate
    private LocalDateTime changedAt;

    private UUID changedBy;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();

        if (createdBy == null) {
            createdBy = AuditConstants.Audit.CREATED_BY;
        }
    }

    @PreUpdate
    public void preUpdate() {
        changedAt = LocalDateTime.now();

        if (changedBy == null) {
            changedBy = AuditConstants.Audit.CHANGED_BY;
        }
    }
}