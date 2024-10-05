package com.riwi.project.api.controller;

import com.riwi.project.api.dto.request.ProjectRequest;
import com.riwi.project.domain.model.ProjectEntity;
import com.riwi.project.domain.services.ProjectService;
import com.riwi.project.domain.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<ProjectEntity> createProject(@RequestBody ProjectRequest projectRequest) {
        ProjectEntity createdProject = projectService.create(projectRequest);

        // Enviar notificación por correo electrónico
        String emailBody = "Se ha creado un nuevo proyecto: " + createdProject.getName();
        emailService.sendEmail("destinatario@correo.com", "Nuevo Proyecto Creado", emailBody);

        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<ProjectEntity>> getAllProjects() {
        return new ResponseEntity<>(projectService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/readById/{id}")
    public ResponseEntity<ProjectEntity> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectEntity> updateProject(@PathVariable Long id, @RequestBody ProjectRequest projectRequest) {
        ProjectEntity updatedProject = projectService.update(id, projectRequest);

        // Enviar notificación por correo electrónico al actualizar un proyecto
        String emailBody = "El proyecto con ID: " + id + " ha sido actualizado.";
        emailService.sendEmail("destinatario@correo.com", "Proyecto Actualizado", emailBody);

        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);

        // Enviar notificación por correo electrónico al eliminar un proyecto
        String emailBody = "El proyecto con ID: " + id + " ha sido eliminado.";
        emailService.sendEmail("destinatario@correo.com", "Proyecto Eliminado", emailBody);

        return ResponseEntity.noContent().build();
    }
}
