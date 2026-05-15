package leonardolucasbs.todolist.auth.models.dto;

import leonardolucasbs.todolist.tasks.models.dto.TaskDTO;

import java.util.List;
import java.util.UUID;

public record PublicUserDTO(UUID id, String name, String email, List<TaskDTO> tasks) {
}
