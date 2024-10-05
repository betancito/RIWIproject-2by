package com.riwi.project.api.dto.response;


import lombok.Data;

@Data
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private String status;
}

