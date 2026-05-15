package leonardolucasbs.todolist.tasks.models.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record LabelDTO(
        UUID id,
        @NotBlank(message = "The label name cannot be blank.")
        String name
) {
}

