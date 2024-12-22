package projectKDG.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import projectKDG.domain.NotificationTracker;
import projectKDG.service.NotificationTrackerService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/notifications")
public class NotificationStreamController {

    private final NotificationTrackerService notificationTrackerService;
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public NotificationStreamController(NotificationTrackerService notificationTrackerService) {
        this.notificationTrackerService = notificationTrackerService;
    }

    // SSE connection
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamNotifications(@RequestParam int userId) {
        SseEmitter emitter = new SseEmitter(0L); // 0L for no timeout
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        // Send initial unseen notifications on connect
        List<NotificationTracker> unseenNotifications = notificationTrackerService.getUnseenNotificationsByUser(userId);
        unseenNotifications.forEach(notification -> sendNotification(emitter, notification));

        return emitter;
    }

    // Call this method whenever a new notification is created
    public void broadcastNotification(NotificationTracker notification) {
        emitters.forEach(emitter -> sendNotification(emitter, notification));
    }

    private void sendNotification(SseEmitter emitter, NotificationTracker notification) {
        try {
            emitter.send(notification);
        } catch (IOException e) {
            emitter.complete();
            emitters.remove(emitter);
        }
    }
}
