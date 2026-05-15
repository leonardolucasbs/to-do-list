package leonardolucasbs.todolist.tasks.repositories;

import leonardolucasbs.todolist.tasks.models.entities.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, UUID> {
}
