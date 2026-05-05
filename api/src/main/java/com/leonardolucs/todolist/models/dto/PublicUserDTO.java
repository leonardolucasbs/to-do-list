package com.leonardolucs.todolist.models.dto;

import java.util.List;
import java.util.UUID;

public record PublicUserDTO(UUID id, String name, String email, List<TaskDTO> tasks) {
}
