package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectKDG.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);
}