package projectKDG.service.notification;

import org.springframework.stereotype.Component;
import projectKDG.domain.NotificationPreference;

@Component
public class NotificationContext {
    private final NotificationStrategyFactory strategyFactory;

    public NotificationContext(NotificationStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public void executeStrategy(Notification notification, NotificationPreference strategyType) {
        NotificationStrategy strategy = strategyFactory.createStrategy(strategyType);
        strategy.notify(notification);
    }
}