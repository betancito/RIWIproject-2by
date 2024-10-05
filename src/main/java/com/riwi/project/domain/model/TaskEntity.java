package com.riwi.project.domain.model;

import com.riwi.project.domain.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name = "task")
@Data
@Entity
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "project_id",nullable = false)
    private ProjectEntity project;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private List<UserEntity> user;

}
