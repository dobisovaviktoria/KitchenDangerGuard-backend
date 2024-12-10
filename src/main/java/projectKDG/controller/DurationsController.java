package projectKDG.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import projectKDG.service.SensorDataService;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/history")
public class DurationsController {

    private final SensorDataService sensorDataService;

    public DurationsController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @GetMapping("/stove-durations")
    public Map<String, Integer> getStoveDurations(@RequestParam String date, @RequestParam int userId) {
        LocalDate selectedDate = LocalDate.parse(date);
        return sensorDataService.getStoveOnDurationsPerHour(selectedDate, userId);
    }
}
