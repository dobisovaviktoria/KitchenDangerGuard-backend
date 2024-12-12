package projectKDG.service;

import org.springframework.stereotype.Service;
import projectKDG.controller.SensorDataController;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;
import projectKDG.repository.NotificationTrackerRepository;
import projectKDG.repository.SensorDataRepository;
import projectKDG.repository.UserRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationTrackerService {

    private final NotificationTrackerRepository notificationTrackerRepository;
    private final UserRepository userRepository;
    private SensorDataRepository sensorDataRepository;

    public NotificationTrackerService(NotificationTrackerRepository notificationTrackerRepository, UserRepository userRepository, SensorDataRepository sensorDataRepository) {
        this.notificationTrackerRepository = notificationTrackerRepository;
        this.userRepository = userRepository;
        this.sensorDataRepository = sensorDataRepository;
    }

    public NotificationTracker createNotification(int userId) {
        // Fetch user from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Get the average temperature
        LocalDateTime time = LocalDateTime.now().minusMinutes(10);
        Double averageTemperature = sensorDataRepository.findAverageTemperature(time);

        // Create and save the notification
        NotificationTracker notification = new NotificationTracker(user, LocalDateTime.now(), averageTemperature);
        notification.setMessage("KDG Alert. Stove is unattended and average temperature value is: " + averageTemperature);

        return notificationTrackerRepository.save(notification);
    }

    public Map<String, Integer> getNotificationsPerHour(int userId,LocalDate selectedDate) {
        List<Object[]> queryResults = notificationTrackerRepository.countNotificationsPerHour(userId,selectedDate);
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

    // Method to calculate the start and end of the week
    // This method returns a Map where the key is the day of the week (e.g., "Monday", "Tuesday", etc.)
    // and the value is the count of notifications for that day.
    public Map<LocalDate, Long> getWeeklyNotifications(int userId, LocalDate selectedDate) {
        // Subtract 6 days from the selected date to get the start of the week (Monday).
        LocalDateTime startOfWeek = selectedDate.minusDays(6).atStartOfDay(); // Start of the week

        // The selected date is already the end of the week (Sunday), set it to 23:59:59 for the end of the day.
        LocalDateTime endOfWeek = selectedDate.atTime(23, 59, 59); // End of the week

        // Fetch notifications for the week
        List<NotificationTracker> notifications = notificationTrackerRepository.findNotificationsForWeek(userId, startOfWeek, endOfWeek);

        // Group notifications by date and count them
        Map<LocalDate, Long> dailyNotifications = notifications.stream()
                .collect(Collectors.groupingBy(
                        notification -> notification.getSentAt().toLocalDate(), // Group by date
                        Collectors.counting() // Count the notifications for each date
                ));

        // Return the map with the count of notifications per date
        return dailyNotifications;
    }

    public List<NotificationTracker> getNotificationsByUser(int userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        // Fetch the latest notifications for the user with pagination
        return notificationTrackerRepository.findLatestNotificationsByUser(user, pageable);
    }

}

