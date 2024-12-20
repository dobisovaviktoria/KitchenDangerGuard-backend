package projectKDG.service.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SmsNotifier implements NotificationStrategy {
    private static final Logger log = LoggerFactory.getLogger(SmsNotifier.class);

    @Override
    public void notify(Notification notification) {
        log.info("Sending SMS notification to {}", notification.getDestination());
    }
}