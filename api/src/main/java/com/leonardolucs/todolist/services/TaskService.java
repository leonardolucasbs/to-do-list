package com.leonardolucs.todolist.services;

import com.leonardolucs.todolist.models.dto.CreateTaskDTO;
import com.leonardolucs.todolist.models.dto.TaskDTO;
import com.leonardolucs.todolist.models.entities.Description;
import com.leonardolucs.todolist.models.entities.Task;
import com.leonardolucs.todolist.models.entities.User;
import com.leonardolucs.todolist.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final DescriptionService descriptionService;
    private final UserService userService;

    public Task createTask(CreateTaskDTO taskDTO, UUID userId) {
        User user = userService.findEntityById(userId);
        Task newTask = new Task();
        newTask.setTitle(taskDTO.title());
        newTask.setDateTime(taskDTO.dateTime());
        newTask.setPriority(taskDTO.priority());
        newTask.setUser(user);

        taskRepository.save(newTask);

        if (taskDTO.description() != null && !taskDTO.description().isBlank()) {
            Description description = descriptionService.createDescription(taskDTO.description(), newTask);
            newTask.setDescription(description);
        }

        return taskRepository.save(newTask);
    }

    public TaskDTO getTaskById(UUID taskId, UUID userId) {
        User users = userService.findEntityById(userId);

        List<TaskDTO> taskList = getAllTasks(users.getId());

        return taskList.stream()
                .filter(t -> taskId.equals(t.id()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(" Task not found") );

    }

    public List<TaskDTO> getAllTasks(UUID userId) {
        User user = userService.findEntityById(userId);

        return user.getTasks()
                .stream()
                .map(Task::toDto)
                .collect(Collectors.toList());
    }

    public void deleteTask(UUID taskId, UUID userId) {
        User user = userService.findEntityById(userId);

        List<TaskDTO> taskList = getAllTasks(user.getId());

        TaskDTO taskFound = taskList.stream()
                .filter(t -> taskId.equals(t.id()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.deleteById(taskFound.id());
    }
}
