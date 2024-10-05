package com.riwi.project.domain.services;

import com.riwi.project.api.dto.request.ProjectRequest;
import com.riwi.project.domain.model.ProjectEntity;
import com.riwi.project.domain.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    public ProjectEntity readById(Long id){
        return projectRepo.findById(id).orElse(null);
    }

    public void delete(Long id){
         projectRepo.deleteById(id);
    }

    public List<ProjectEntity> readAll(){
        return projectRepo.findAll();
    }

    public ProjectEntity create(ProjectRequest projectRequest){
        ProjectEntity project = new ProjectEntity();
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        return projectRepo.save(project);
    }

    public ProjectEntity update(Long id, ProjectRequest projectRequest){
      ProjectEntity project = projectRepo.findById(id).orElse(null);
        if (projectRequest.getName() != null) {
            project.setName(projectRequest.getName());
        }
        if (projectRequest.getDescription() != null) {
            project.setDescription(projectRequest.getDescription());
        }
        return projectRepo.save(project);
    }

}
