package com.leonardolucs.todolist.models.dto;

import com.leonardolucs.todolist.models.enums.Priority;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDTO(
        UUID id,
        String title,
        LocalDateTime dateTime,
        String description,
        Priority priority
) {
}
