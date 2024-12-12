package projectKDG.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;
import projectKDG.repository.SensorDataRepository;
import projectKDG.repository.UserRepository;
import projectKDG.service.NotificationTrackerService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final NotificationTrackerService notificationTrackerService;

    @Autowired
    public ProfileController(UserRepository userRepository, NotificationTrackerService notificationTrackerService) {
        this.userRepository = userRepository;
        this.notificationTrackerService = notificationTrackerService;
    }

    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        model.addAttribute("user", user);
        Pageable pageable = PageRequest.of(0, 10);


        List<NotificationTracker> notifications = notificationTrackerService.getNotificationsByUser(user.getUserID(), pageable);

        // Set the message for each notification
        for (NotificationTracker notification : notifications) {
            String message = "KDG Alert. Stove is unattended and average temperature value is: " + notification.getAverageTemperature();
            notification.setMessage(message);
        }

        model.addAttribute("notifications", notifications);

        return "profile";
    }
}