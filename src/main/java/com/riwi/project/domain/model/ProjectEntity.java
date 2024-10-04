package com.riwi.project.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "project")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}
