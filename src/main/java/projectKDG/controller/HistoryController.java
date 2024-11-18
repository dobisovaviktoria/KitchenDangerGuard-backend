package projectKDG.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projectKDG.service.MotionService;
import projectKDG.service.TemperatureService;

@Controller
public class HistoryController {

    private final MotionService motionService;
    private final TemperatureService temperatureService;

    public HistoryController(MotionService motionService, TemperatureService temperatureService) {
        this.motionService = motionService;
        this.temperatureService = temperatureService;
    }

    @GetMapping("/history")
    public String getHistory(Model model) {
        // Fetch the latest 10 entries for both Motion and Temperature
        model.addAttribute("motions", motionService.getLatestMotions());
        model.addAttribute("temperatures", temperatureService.getLatestTemperatures());
        return "history";
    }
}
