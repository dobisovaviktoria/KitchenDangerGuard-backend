package projectKDG.service.notification;

import org.springframework.stereotype.Component;
import projectKDG.domain.NotificationPreference;
import projectKDG.domain.NotificationTracker;
import projectKDG.repository.NotificationTrackerRepository;

import java.time.LocalDateTime;

@Component
public class WebNotifier implements NotificationStrategy {
    @Override
    public void notify(Notification notification) {
        System.out.println("Web notification processed for: " + notification.getDestination());
    }
}
