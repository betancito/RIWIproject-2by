package com.riwi.project.domain.services;

import com.riwi.project.api.dto.request.ProjectRequest;
import com.riwi.project.domain.model.ProjectEntity;
import com.riwi.project.domain.model.TaskEntity;
import com.riwi.project.domain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    public ProjectEntity readById(Long id) {
        return projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public void delete(Long id) {
        projectRepo.deleteById(id);
    }

    public List<ProjectEntity> readAll() {
        return projectRepo.findAll();
    }

    public ProjectEntity create(ProjectRequest projectRequest) {
        ProjectEntity project = new ProjectEntity();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());

        // Asociar tareas si existen en la solicitud
        if (projectRequest.getTasks() != null) {
            List<TaskEntity> tasks = projectRequest.getTasks().stream().map(taskRequest -> {
                TaskEntity task = new TaskEntity();
                task.setName(taskRequest.getName());
                task.setDescription(taskRequest.getDescription());
                task.setProject(project);
                return task;
            }).collect(Collectors.toList());
            project.setTasks(tasks);
        }

        return projectRepo.save(project);
    }

    public ProjectEntity update(Long id, ProjectRequest projectRequest) {
        ProjectEntity project = projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

        if (projectRequest.getName() != null) {
            project.setName(projectRequest.getName());
        }
        if (projectRequest.getDescription() != null) {
            project.setDescription(projectRequest.getDescription());
        }

        return projectRepo.save(project);
    }
}
