package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectKDG.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
