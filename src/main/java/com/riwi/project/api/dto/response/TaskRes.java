package com.riwi.project.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskRes {
    private String name;
    private String description;
    private String status;
}
