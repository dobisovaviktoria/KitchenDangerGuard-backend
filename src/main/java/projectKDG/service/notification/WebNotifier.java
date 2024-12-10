package projectKDG.service.notification;

import org.springframework.stereotype.Component;
import projectKDG.domain.NotificationPreference;
import projectKDG.domain.NotificationTracker;
import projectKDG.repository.NotificationTrackerRepository;

import java.time.LocalDateTime;

@Component
public class WebNotifier implements NotificationStrategy {
//    private final NotificationTrackerRepository notificationTrackerRepository;
//
//    public WebNotifier(NotificationTrackerRepository notificationTrackerRepository) {
//        this.notificationTrackerRepository = notificationTrackerRepository;
//    }
    @Override
    public void notify(Notification notification) {
//        NotificationTracker notificationTracker = new NotificationTracker();
//        notificationTracker.setUser(notification.getUser());
//        notificationTracker.setSentAt(LocalDateTime.now());
//        notificationTracker.setMessage(notification.getMessage());
//
//        notificationTrackerRepository.save(notificationTracker);
    }
}
