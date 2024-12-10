package projectKDG.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;
import projectKDG.repository.UserRepository;
import projectKDG.service.NotificationTrackerService;
import projectKDG.service.UserService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/notifications")
public class NotificationTrackerController {

    private final NotificationTrackerService notificationeTrackerService;
    private final UserRepository userRepository;

    public NotificationTrackerController(NotificationTrackerService notificationService, UserRepository userRepository) {
        this.notificationeTrackerService = notificationService;
        this.userRepository = userRepository;
    }




}
