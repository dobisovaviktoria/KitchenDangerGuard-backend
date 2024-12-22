package projectKDG.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projectKDG.domain.NotificationTracker;
import projectKDG.domain.SensorData;
import projectKDG.domain.User;
import projectKDG.service.NotificationTrackerService;
import projectKDG.service.SensorDataService;

import java.util.List;

@Controller
public class MyHistoryController {

    private final SensorDataService sensorDataService;

    public MyHistoryController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @GetMapping("/my-history")
    public String myHistoryPage(HttpSession session, Model model) {
        // Get the logged-in user from the session
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<SensorData> sensorDataList = sensorDataService.getSensorDataByUserId(user.getUserID());

        // Add data to the model
        model.addAttribute("user", user);
        model.addAttribute("sensorDataList", sensorDataList);

        return "my-history";
    }
}