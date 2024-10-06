package com.riwi.project.api.dto.request;

import com.riwi.project.domain.enums.TaskStatus;
import com.riwi.project.domain.model.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskReq {
    private Long id;
    private String name;
    private String description;
    private TaskStatus status;
    private Long projectId;
    private List<UserEntity> assignedTo;
}
