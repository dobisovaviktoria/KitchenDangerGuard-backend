package projectKDG.service.notification;

public class NotificationContext {
    private NotificationStrategy strategy;

    public NotificationContext(NotificationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(NotificationStrategy strategy) {}

    public void executeStrategy(Notification notification) {
        strategy.notify(notification);
    }
}
