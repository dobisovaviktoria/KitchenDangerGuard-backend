package projectKDG.service;

import org.springframework.stereotype.Service;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;
import projectKDG.repository.NotificationTrackerRepository;
import projectKDG.repository.UserRepository;

import java.time.LocalDateTime;

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
}
