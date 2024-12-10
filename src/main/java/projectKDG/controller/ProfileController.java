package projectKDG.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;
import projectKDG.repository.NotificationTrackerRepository;
import projectKDG.service.notification.Notification;
import projectKDG.service.notification.NotificationContext;

import java.util.List;

@Controller
public class ProfileController {
    private NotificationTrackerRepository notificationTrackerRepository;

    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }
        model.addAttribute("user", user);
//
//        List<NotificationTracker> notifications = notificationTrackerRepository.findByUser(user);
//        model.addAttribute("notifications", notifications);

        return "profile";
    }
}
