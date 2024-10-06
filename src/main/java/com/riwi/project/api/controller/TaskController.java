package com.riwi.project.api.controller;

import com.riwi.project.api.dto.request.TaskReq;
import com.riwi.project.api.dto.response.TaskRes;
import com.riwi.project.domain.model.TaskEntity;
import com.riwi.project.domain.services.TaskService;
import com.riwi.project.domain.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/create")
    public ResponseEntity<TaskRes> createTask(@RequestBody TaskReq taskRequest) {
        TaskRes createdTask = taskService.create(taskRequest);

        // Enviar notificación por correo electrónico
        String emailBody = "Se ha creado una nueva tarea: " + createdTask.getName();
        emailService.sendEmail("destinatario@correo.com", "Nueva Tarea Creada", emailBody);

        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping("/readAll")
    public ResponseEntity<List<TaskEntity>> getAllTasks() {
        return new ResponseEntity<>(taskService.readAll(), HttpStatus.OK);
    }

    @GetMapping("/readById/{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.readById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskRes> updateTask(@PathVariable Long id, @RequestBody TaskReq taskRequest) {
        TaskRes updatedTask = taskService.update(id, taskRequest);

        // Enviar notificación por correo electrónico al actualizar una tarea
        String emailBody = "La tarea con ID: " + id + " ha sido actualizada.";
        emailService.sendEmail("destinatario@correo.com", "Tarea Actualizada", emailBody);

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);

        // Enviar notificación por correo electrónico al eliminar una tarea
        String emailBody = "La tarea con ID: " + id + " ha sido eliminada.";
        emailService.sendEmail("destinatario@correo.com", "Tarea Eliminada", emailBody);

        return ResponseEntity.noContent().build();
    }
}
