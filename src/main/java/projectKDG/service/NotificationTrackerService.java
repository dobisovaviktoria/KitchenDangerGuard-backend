package projectKDG.service;

import org.springframework.stereotype.Service;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;
import projectKDG.repository.NotificationTrackerRepository;
import projectKDG.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationTrackerService {

    private final NotificationTrackerRepository notificationTrackerRepository;
    private final UserRepository userRepository;

    public NotificationTrackerService(NotificationTrackerRepository notificationTrackerRepository, UserRepository userRepository) {
        this.notificationTrackerRepository = notificationTrackerRepository;
        this.userRepository = userRepository;
    }

    public NotificationTracker createNotification(int userId) {
        // Fetch user from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Create and save the notification
        NotificationTracker notification = new NotificationTracker(user, LocalDateTime.now());
        return notificationTrackerRepository.save(notification);
    }

    public Map<String, Integer> getNotificationsPerHour(LocalDate selectedDate) {
        List<Object[]> queryResults = notificationTrackerRepository.countNotificationsPerHour(selectedDate);
        Map<String, Integer> notificationsPerHour = new LinkedHashMap<>();

        // Initialize all hours with 0 notifications
        for (int i = 0; i < 24; i++) {
            String hour = String.format("%02d:00", i);
            notificationsPerHour.put(hour, 0);
        }

        // Populate the hours with counts from the query results
        for (Object[] result : queryResults) {
            Integer hour = (Integer) result[0];  // The hour (0-23)
            Integer count = ((Number) result[1]).intValue();  // The notification count for this hour

            String formattedHour = String.format("%02d:00", hour);
            notificationsPerHour.put(formattedHour, count);
        }

        return notificationsPerHour;
    }
}
