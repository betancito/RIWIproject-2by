package com.riwi.project.domain.services;

import com.riwi.project.api.dto.request.TaskRequest;
import com.riwi.project.domain.model.TaskEntity;
import com.riwi.project.domain.repository.TaskRepository; // Asegúrate de que este import esté correcto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskEntity create(TaskRequest taskRequest) {
        TaskEntity task = new TaskEntity();
        task.setName(taskRequest.getName());

        return taskRepository.save(task);
    }

    public List<TaskEntity> readAll() {
        return taskRepository.findAll();
    }

    public TaskEntity readById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public TaskEntity update(Long id, TaskRequest taskRequest) {
        TaskEntity task = readById(id);
        if (task != null) {
            task.setName(taskRequest.getName());
            return taskRepository.save(task);
        }
        return null;
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}

