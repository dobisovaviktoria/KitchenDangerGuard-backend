package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotificationTrackerRepository extends JpaRepository<NotificationTracker, Long> {
    List<NotificationTracker> findByUser(User user);

    @Query("""
            SELECT EXTRACT(HOUR FROM nt.sentAt) AS hour, COUNT(nt) AS count
            FROM NotificationTracker nt
            WHERE DATE(nt.sentAt) = :selectedDate
            GROUP BY EXTRACT(HOUR FROM nt.sentAt)
            ORDER BY hour
            """)
    List<Object[]> countNotificationsPerHour(@Param("selectedDate") LocalDate selectedDate);
}

