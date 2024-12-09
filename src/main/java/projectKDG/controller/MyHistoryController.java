package projectKDG.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projectKDG.domain.User;
import projectKDG.service.MotionService;
import projectKDG.service.StoveService;
import projectKDG.service.TemperatureService;

@Controller
public class MyHistoryController {
    private final MotionService motionService;
    private final TemperatureService temperatureService;
    private final StoveService stoveService;

    public MyHistoryController(MotionService motionService, TemperatureService temperatureService, StoveService stoveService) {
        this.motionService = motionService;
        this.temperatureService = temperatureService;
        this.stoveService = stoveService;
    }

    @GetMapping("/my-history")
    public String myHistoryPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }
        model.addAttribute("user", user);
        return "my-history";
    }
}
