package com.leonardolucs.todolist.models.dto;

import com.leonardolucs.todolist.models.enums.Priority;

import java.time.LocalDateTime;

public record TaskDTO(
        Long id,
        String title,
        LocalDateTime dateTime,
        String description,
        Priority priority
) {
}
