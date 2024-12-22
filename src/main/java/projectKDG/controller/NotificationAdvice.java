package projectKDG.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;
import projectKDG.domain.NotificationPreference;
import projectKDG.service.NotificationTrackerService;

import java.util.List;

@ControllerAdvice
public class NotificationAdvice {

    private final NotificationTrackerService notificationTrackerService;

    @Autowired
    public NotificationAdvice(NotificationTrackerService notificationTrackerService) {
        this.notificationTrackerService = notificationTrackerService;
    }

    @ModelAttribute("unseenNotifications")
    public List<NotificationTracker> addUnseenNotificationsToModel(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user != null && user.getNotificationPreference() == NotificationPreference.WEBSITE) {
            List<NotificationTracker> unseenNotifications = notificationTrackerService.getUnseenNotificationsByUser(user.getUserID());
            unseenNotifications.forEach(notification ->
                    notification.setMessage("KDG Alert. Stove is unattended and average temperature is: " + notification.getAverageTemperature())
            );
            return unseenNotifications;
        }
        return List.of();
    }
}
