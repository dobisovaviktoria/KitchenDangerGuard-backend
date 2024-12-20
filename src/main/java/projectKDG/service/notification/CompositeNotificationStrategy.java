package projectKDG.service.notification;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompositeNotificationStrategy implements NotificationStrategy {
    List<NotificationStrategy> notificationStrategies;

    public CompositeNotificationStrategy(List<NotificationStrategy> notificationStrategies) {
        this.notificationStrategies = notificationStrategies;
    }

    @Override
    public void notify(Notification notification) {
        notificationStrategies.forEach(strategy -> strategy.notify(notification));
    }
}