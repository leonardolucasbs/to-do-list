package leonardolucasbs.todolist.auth.repositories;

import leonardolucasbs.todolist.auth.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmailAndPassword(String email, String password);
    Optional<User> findUserByEmail(String email);
}
