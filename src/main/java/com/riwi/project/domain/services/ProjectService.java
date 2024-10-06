package com.riwi.project.domain.services;

import com.riwi.project.api.dto.request.ProjectReq;
import com.riwi.project.api.dto.response.ProjectRes;
import com.riwi.project.api.utils.TransformUtil;
import com.riwi.project.domain.model.ProjectEntity;
import com.riwi.project.domain.repository.ProjectRepository;
import com.riwi.project.infrastructure.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private TransformUtil transformUtil;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    public ProjectEntity readById(Long id) {
        return projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public void delete(Long id) {
        projectRepo.deleteById(id);
    }

    public List<ProjectEntity> readAll() {
        return projectRepo.findAll();
    }

    public ProjectRes create(ProjectReq projectReq) {
        ProjectEntity newProject = transformUtil.transformProjectEntity(projectReq);
        newProject.setCreatedAt(new Date(System.currentTimeMillis()));
        newProject.setUpdatedAt(new Date(System.currentTimeMillis()));
        projectRepo.save(newProject);
        return transformUtil.transformProjectRes(newProject);
    }

    public ProjectRes update(Long id, ProjectReq projectRequest) {

        ProjectEntity updatedProject = projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

        if (updatedProject != null) {
            updatedProject = transformUtil.transformProjectEntity(projectRequest);
            updatedProject.setUpdatedAt(new Date(System.currentTimeMillis()));
            projectRepo.save(updatedProject);
        }

        return transformUtil.transformProjectRes(updatedProject);
    }
}

