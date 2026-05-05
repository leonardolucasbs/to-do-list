package com.leonardolucs.todolist.repositories;

import com.leonardolucs.todolist.models.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
