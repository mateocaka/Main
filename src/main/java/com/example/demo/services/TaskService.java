package com.example.demo.services;

import com.example.demo.models.Task;
import com.example.demo.models.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        System.out.println("Creating task: " + task.getName());
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        System.out.println("Fetching all tasks");
        return (List<Task>) taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        System.out.println("Fetching task with ID: " + id);
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, Task taskDetails) {
        System.out.println("Updating task with ID: " + id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
        task.setName(taskDetails.getName());
        task.setDescription(taskDetails.getDescription());
        task.setPerson(taskDetails.getPerson());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        System.out.println("Deleting task with ID: " + id);
        taskRepository.deleteById(id);
    }
}