package leonardolucasbs.todolist.tasks.models.dto;

import leonardolucasbs.todolist.tasks.models.enums.Priority;

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
