package com.riwi.project.api.utils;

import com.riwi.project.api.dto.request.ProjectReq;
import com.riwi.project.api.dto.request.TaskReq;
import com.riwi.project.api.dto.request.UserPublicReq;
import com.riwi.project.api.dto.request.UserReq;
import com.riwi.project.api.dto.response.ProjectRes;
import com.riwi.project.api.dto.response.TaskRes;
import com.riwi.project.api.dto.response.UserRes;
import com.riwi.project.domain.model.ProjectEntity;
import com.riwi.project.domain.model.TaskEntity;
import com.riwi.project.domain.model.UserEntity;
import com.riwi.project.domain.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransformUtil {

    @Autowired
    private ProjectService projectService;

//Utils to transform Users
    //to userEntity from UserRequest
    public UserEntity transformUserEntity(UserReq userReq){
        return UserEntity.builder()
                .id(userReq.getId())
                .username(userReq.getUsername())
                .email(userReq.getEmail())
                .password(userReq.getPassword())
                .role(userReq.getRole())
                .build();
    }

    //to userResponse from UserEntity
    public UserRes transformUserRes(UserEntity userEntity){
        return UserRes.builder()
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
                .build();
    }

//Utils to transform projects
    //to ProjectEntity from ProjectReq
    public ProjectEntity transformProjectEntity(ProjectReq projectReq){
        return ProjectEntity.builder()
                .id(projectReq.getId())
                .name(projectReq.getName())
                .description(projectReq.getDescription())
                .build();
    }

    //to ProjectRes from ProjectEntity
    public ProjectRes transformProjectRes(ProjectEntity projectEntity){
        return ProjectRes.builder()
                .name(projectEntity.getName())
                .description(projectEntity.getDescription())
                .build();
    }

//Utils to transform Tasks
    //from TaskReq to TaskEntity
    public TaskEntity transformTaskEntity(TaskReq taskEntity){
        return TaskEntity.builder()
                .id(taskEntity.getId())
                .name(taskEntity.getName())
                .description(taskEntity.getDescription())
                .status(taskEntity.getStatus())
                .project(projectService.readById(taskEntity.getProjectId()))
                .assignedTo(taskEntity.getAssignedTo())
                .build();
    }

    public TaskRes transformTaskRes(TaskEntity taskEntity){
        return TaskRes.builder()
                .name(taskEntity.getName())
                .description(taskEntity.getDescription())
                .status(taskEntity.getDescription())
                .build();
    }

}
