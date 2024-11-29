package projectKDG.service.notification;

import org.springframework.stereotype.Component;

@Component
public class WebNotifier implements NotificationStrategy {
    @Override
    public void notify(Notification notification) {
    }
}
