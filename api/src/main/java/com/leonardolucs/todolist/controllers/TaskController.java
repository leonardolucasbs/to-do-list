package com.leonardolucs.todolist.controllers;

import com.leonardolucs.todolist.models.dto.CreateTaskDTO;
import com.leonardolucs.todolist.models.dto.TaskDTO;
import com.leonardolucs.todolist.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Void> createTask(@Valid @RequestBody CreateTaskDTO taskDTO, @RequestParam UUID userId) {
        taskService.createTask(taskDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks(@RequestParam UUID userId) {
        return ResponseEntity.ok(taskService.getAllTasks(userId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTaskById(@PathVariable UUID taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/{taskId}")
//    public ResponseEntity<?> updateTask(@PathVariable UUID taskId, @RequestParam UUID userId) {
//        taskService.updateTask(taskId, userId );
//        return ResponseEntity.ok().build();
//    }

}
