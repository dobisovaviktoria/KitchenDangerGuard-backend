package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectKDG.domain.NotificationTracker;

@Repository
public interface NotificationTrackerRepository extends JpaRepository<NotificationTracker, Long> {
}

