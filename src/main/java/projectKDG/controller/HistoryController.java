package projectKDG.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projectKDG.service.MotionService;
import projectKDG.service.StoveService;
import projectKDG.service.TemperatureService;

@Controller
public class HistoryController {

    private final MotionService motionService;
    private final TemperatureService temperatureService;
    private final StoveService stoveService;

    public HistoryController(MotionService motionService, TemperatureService temperatureService, StoveService stoveService) {
        this.motionService = motionService;
        this.temperatureService = temperatureService;
        this.stoveService = stoveService;
    }

    @GetMapping("/history")
    public String getHistory(Model model) {
        // Fetch the latest 20 entries for both Motion and Temperature
        var motions = motionService.getLatestMotions();
        var motionsAll = motionService.getMotions();
        var temperatures = temperatureService.getLatestTemperatures();
        var temperaturesAll = stoveService.getOrderedTemperatures();
        model.addAttribute("motions", motionService.getLatestMotions());
        model.addAttribute("temperatures", temperatureService.getLatestTemperatures());

        return "history";
    }
}

