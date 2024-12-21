package projectKDG.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projectKDG.domain.ArduinoDevice;
import projectKDG.domain.NotificationPreference;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.User;
import projectKDG.repository.UserRepository;
import projectKDG.service.ArduinoDeviceService;
import projectKDG.service.NotificationTrackerService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final NotificationTrackerService notificationTrackerService;
    private final ArduinoDeviceService arduinoDeviceService;

    @Autowired
    public ProfileController(UserRepository userRepository, NotificationTrackerService notificationTrackerService, ArduinoDeviceService arduinoDeviceService) {
        this.userRepository = userRepository;
        this.notificationTrackerService = notificationTrackerService;
        this.arduinoDeviceService = arduinoDeviceService;
    }

    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }

        ArduinoDevice arduinoDevice = arduinoDeviceService.getArduinoDeviceByUserId(user.getUserID());
        model.addAttribute("arduinoDevice", arduinoDevice);

        model.addAttribute("user", user);
        model.addAttribute("preferences", List.of(NotificationPreference.values()));
        model.addAttribute("maxDate", java.time.LocalDate.now().toString());
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

    @PostMapping("/edit-profile")
    public String updateProfile(HttpSession session, Model model, @RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String phone,
                                @RequestParam LocalDate dob,
                                @RequestParam NotificationPreference notificationPreference,
                                @RequestParam(required = false) String password) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("preferences", List.of(NotificationPreference.values()));
        model.addAttribute("maxDate", java.time.LocalDate.now().toString());
        user.setUserName(username);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAge(dob);
        user.setNotificationPreference(notificationPreference);

        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(password);
        }

        userRepository.save(user);
        return "redirect:/profile";
    }

    @GetMapping("/edit-profile")
    public String editProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("user", user);
        model.addAttribute("preferences", List.of(NotificationPreference.values()));
        model.addAttribute("maxDate", java.time.LocalDate.now().toString());
        return "edit-profile";
    }
}