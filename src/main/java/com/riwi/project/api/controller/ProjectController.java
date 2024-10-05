package com.riwi.project.api.controller;

import com.riwi.project.api.dto.request.ProjectRequest;
import com.riwi.project.domain.model.ProjectEntity;
import com.riwi.project.domain.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    @Autowired
    public ProjectService projectService;

    @PostMapping("/create")
    public ProjectEntity createProject(@RequestBody ProjectRequest projectRequest){
        return projectService.create(projectRequest);
    }
    @GetMapping("/readAll")
    public ResponseEntity<List<ProjectEntity>> getAllProjects(){
        return new ResponseEntity<>(projectService.readAll(), HttpStatus.OK);
    }

    @GetMapping("readById/{id}")
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable Long id){
        return ResponseEntity.ok(projectService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ProjectEntity updateProject(@PathVariable Long id, @RequestBody ProjectRequest project){
        return projectService.update(id,project);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id){
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
