package leonardolucasbs.todolist.tasks.repositories;

import leonardolucasbs.todolist.tasks.models.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
