package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;

import java.util.List;

@Repository
public interface NotificationTrackerRepository extends JpaRepository<NotificationTracker, Long> {
    List<NotificationTracker> findByUser(User user);
}

