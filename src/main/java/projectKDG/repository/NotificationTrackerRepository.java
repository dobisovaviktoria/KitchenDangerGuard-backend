package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationTrackerRepository extends JpaRepository<NotificationTracker, Long> {
    @Query("SELECT n FROM NotificationTracker n WHERE n.user = :user ORDER BY n.sentAt DESC")
    List<NotificationTracker> findLatestNotificationsByUser(@Param("user") User user, Pageable pageable);

    @Query("SELECT n FROM NotificationTracker n WHERE n.user = :user AND n.seen = false AND n.sentAt >= :timeThreshold ORDER BY n.sentAt DESC LIMIT 1")
    List<NotificationTracker> findUnseenNotificationsByUser(@Param("user") User user, @Param("timeThreshold")LocalDateTime timeThreshold);

    @Modifying
    @Query("UPDATE NotificationTracker n SET n.seen = true WHERE n.notificationId IN :ids")
    void markAsSeen(@Param("ids") List<Long> ids);


    @Query("""
            SELECT EXTRACT(HOUR FROM nt.sentAt) AS hour, COUNT(nt) AS count
            FROM NotificationTracker nt
            WHERE DATE(nt.sentAt) = :selectedDate
            AND nt.user.userID = :userId
            GROUP BY EXTRACT(HOUR FROM nt.sentAt)
            ORDER BY hour
            """)
    List<Object[]> countNotificationsPerHour(@Param("userId") int userId,
                                             @Param("selectedDate") LocalDate selectedDate);


    @Query("SELECT n FROM NotificationTracker n WHERE n.user.userID = :userId AND n.sentAt BETWEEN :startOfWeek AND :endOfWeek")
    List<NotificationTracker> findNotificationsForWeek(
            @Param("userId") int userId,
            @Param("startOfWeek") LocalDateTime startOfWeek,
            @Param("endOfWeek") LocalDateTime endOfWeek
    );
}