package com.riwi.project.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.Date;

@MappedSuperclass
@Setter
@Getter
public abstract class Auditable {

    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date updatedAt;
}
