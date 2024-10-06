package com.riwi.project.domain.services;

import com.riwi.project.api.dto.request.TaskReq;
import com.riwi.project.api.dto.response.TaskRes;
import com.riwi.project.api.utils.TransformUtil;
import com.riwi.project.domain.model.TaskEntity;
import com.riwi.project.domain.repository.TaskRepository; // Asegúrate de que este import esté correcto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TransformUtil transformUtil;

    public TaskRes create(TaskReq taskRequest) {
        TaskEntity task = transformUtil.transformTaskEntity(taskRequest);
        task.setCreatedAt(new Date(System.currentTimeMillis()));
        task.setUpdatedAt(new Date(System.currentTimeMillis()));
        taskRepository.save(task);
        return transformUtil.transformTaskRes(task);
    }

    public List<TaskEntity> readAll() {
        return taskRepository.findAll();
    }

    public TaskEntity readById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public TaskRes update(Long id, TaskReq taskRequest) {
        TaskEntity updatedTask = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        if (updatedTask != null) {
            updatedTask.setName(taskRequest.getName());
            updatedTask.setDescription(taskRequest.getDescription());
            updatedTask.setStatus(taskRequest.getStatus());
            updatedTask.setAssignedTo(taskRequest.getAssignedTo());
            updatedTask.setUpdatedAt(new Date(System.currentTimeMillis()));
            taskRepository.save(updatedTask);

            return transformUtil.transformTaskRes(updatedTask);
        }
        return null;
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}

