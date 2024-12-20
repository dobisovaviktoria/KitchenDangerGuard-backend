package projectKDG.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import projectKDG.domain.SensorData;
import projectKDG.service.SensorDataService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class MotionDetectionController {

    private final SensorDataService sensorDataService;

    public MotionDetectionController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @GetMapping("/api/history/motion-data")
    public Map<String, Object> getMotionData(@RequestParam String date, @RequestParam int userId) {
        LocalDate selectedDate = LocalDate.parse(date);
        List<SensorData> sensorDataList = sensorDataService.getSensorDataByUserId(userId)
                .stream()
                .filter(data -> data.getTimestamp().toLocalDate().equals(selectedDate))
                .toList();

        // Extract temperatures and motion statuses
        List<Float> temperatures = sensorDataList.stream()
                .map(SensorData::getTemperatureValue)
                .toList();
        List<Boolean> motionStatuses = sensorDataList.stream()
                .map(SensorData::getMotionStatus)
                .toList();

        // Define temperature bins and labels
        double[] bins = {20, 40, 60, 80, 100, 120, 140};
        String[] labels = {"20-40°C", "40-60°C", "60-80°C", "80-100°C", "100-120°C", "120-140°C"};

        // Count motion and no motion in each bin
        Map<String, long[]> motionCounts = SensorDataService.calculateMotionCountsByTemperatureBins(
                temperatures, motionStatuses, bins, labels);

        return Map.of(
                "temperature_ranges", labels,
                "motion_counts", Map.of(
                        "motion", motionCounts.values().stream().mapToLong(a -> a[1]).toArray(),
                        "no_motion", motionCounts.values().stream().mapToLong(a -> a[0]).toArray()
                )
        );
    }
}