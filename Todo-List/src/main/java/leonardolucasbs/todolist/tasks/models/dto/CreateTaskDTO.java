package leonardolucasbs.todolist.tasks.models.dto;

import leonardolucasbs.todolist.tasks.models.enums.Priority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateTaskDTO(
        @NotBlank(message = "The title cannot be blank.")
        String title,

        @NotNull(message = "The date cannot be null.")
        @FutureOrPresent(message = "The task date cannot be in the past.")
        LocalDateTime dateTime,

        String description,

        @NotNull(message = "The priority cannot be null.")
        Priority priority
) {
}
