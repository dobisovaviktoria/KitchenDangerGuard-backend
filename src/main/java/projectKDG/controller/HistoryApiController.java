package projectKDG.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import projectKDG.service.MotionService;
import projectKDG.service.StoveService;
import projectKDG.service.TemperatureService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HistoryApiController {

    private final MotionService motionService;
    private final TemperatureService temperatureService;
    private final StoveService stoveService;

    public HistoryApiController(MotionService motionService, TemperatureService temperatureService, StoveService stoveService) {
        this.motionService = motionService;
        this.temperatureService = temperatureService;
        this.stoveService = stoveService;
    }

    @GetMapping("/api/motion-data")
    @ResponseBody
    public Map<String, Object> getMotionData() {
        var motions = motionService.getMotions();
        Map<String, Object> response = new HashMap<>();
        response.put("timestamps", motions.stream().map(m -> m.getMotionTimestamp().toString()).toList());
        response.put("statuses", motions.stream().map(m -> m.isMotionSensorStatus() ? 1 : 0).toList());
        return response;
    }

    @GetMapping("/api/temperature-data")
    @ResponseBody
    public Map<String, Object> getTemperatureData() {
        var temperatures = temperatureService.getLatestTemperatures();
        Map<String, Object> response = new HashMap<>();
        response.put("timestamps", temperatures.stream().map(t -> t.getTemperatureTimestamp().toString()).toList());
        response.put("values", temperatures.stream().map(t -> t.getTemperatureSensorValue()).toList());
        return response;
    }

    @GetMapping("/api/stove-durations")
    @ResponseBody
    public Map<String, Object> getStoveDurations() {
        var durations = stoveService.calculateStoveDurations(temperatureService.getLatestTemperatures());
        var durationsInMinutes = durations.stream()
                .map(duration -> {
                    long hours = duration.getDuration().toHours();
                    long minutes = duration.getDuration().toMinutes() % 60;
                    long seconds = duration.getDuration().getSeconds() % 60;
                    return hours * 60 + minutes + seconds / 60.0; // Total minutes
                })
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("durations", durationsInMinutes);
        return response;
    }
}
