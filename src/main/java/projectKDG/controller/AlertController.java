package projectKDG.controller;

////for website
import org.springframework.web.bind.annotation.*;
import projectKDG.service.NotificationTrackerService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class AlertController {

    private final NotificationTrackerService notificationTrackerService;

    public AlertController(NotificationTrackerService notificationTrackerService) {
        this.notificationTrackerService = notificationTrackerService;
    }

    @PostMapping("/mark-as-seen")
    public void markNotificationsAsSeen(@RequestBody List<Long> notificationIds) {
        notificationTrackerService.markNotificationsAsSeen(notificationIds);
    }
}

