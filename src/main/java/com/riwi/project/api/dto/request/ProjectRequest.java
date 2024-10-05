package com.riwi.project.api.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ProjectRequest {
    private String name;
    private String description;
    private List<TaskRequest> tasks;
}
