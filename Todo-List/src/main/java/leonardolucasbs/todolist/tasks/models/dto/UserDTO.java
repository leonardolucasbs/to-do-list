package leonardolucasbs.todolist.tasks.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank(message = "The name cannot be blank.")
        String name,

        @NotBlank(message = "The email cannot be blank.")
        @Email(message = "Invalid email format.")
        String email,

        @NotBlank(message = "The password cannot be blank.")
        @Size(min = 6, message = "The password must be at least 6 characters long.")
        String password

) {
}
