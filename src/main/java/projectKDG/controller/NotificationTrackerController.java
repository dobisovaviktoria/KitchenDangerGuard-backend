package projectKDG.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;
import projectKDG.repository.UserRepository;
import projectKDG.service.NotificationTrackerService;
import projectKDG.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationTrackerController {

    private final NotificationTrackerService notificationeTrackerService;
    private final UserRepository userRepository;

    public NotificationTrackerController(NotificationTrackerService notificationService, UserRepository userRepository) {
        this.notificationeTrackerService = notificationService;
        this.userRepository = userRepository;
    }

    @GetMapping("/hourly")
    public ResponseEntity<Map<String, Integer>> getNotificationsPerHour(@RequestParam String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate selectedDate = LocalDate.parse(date, formatter);

        Map<String, Integer> notificationsPerHour = notificationeTrackerService.getNotificationsPerHour(selectedDate);

        return ResponseEntity.ok(notificationsPerHour);
    }

    @GetMapping("/weekly")
    public ResponseEntity<Map<String, Long>> getWeeklyNotifications(
            @RequestParam int userId,
            @RequestParam String date) {

        // Convert the date string to LocalDate
        LocalDate selectedDate = LocalDate.parse(date);

        // Fetch the weekly data for the given user and selected date (end of the week)
        Map<LocalDate, Long> weeklyData = notificationeTrackerService.getWeeklyNotifications(userId, selectedDate);

        // Convert LocalDate keys to String for JSON serialization
        Map<String, Long> formattedWeeklyData = weeklyData.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(), // Convert LocalDate to String
                        Map.Entry::getValue
                ));

        // Return the data as JSON
        return ResponseEntity.ok(formattedWeeklyData);
    }

}




