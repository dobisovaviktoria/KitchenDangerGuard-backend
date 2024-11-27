package projectKDG.service;

import org.springframework.stereotype.Service;
import projectKDG.domain.StoveUsage;
import projectKDG.domain.Temperature;
import projectKDG.repository.TemperatureRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;

@Service
public class StoveService {

    private final TemperatureRepository temperatureRepository;

    public StoveService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    public List<Temperature> getOrderedTemperatures() {
        return temperatureRepository.findAllOrderByTimestamp();
    }

    public List<StoveUsage> calculateStoveDurations(List<Temperature> temperatures) {
        List<StoveUsage> stoveDurations = new ArrayList<>();
        LocalDateTime stoveStart = null;

        // Validate and ensure temperatures are sorted
        temperatures.sort((t1, t2) -> t1.getTemperatureTimestamp().compareTo(t2.getTemperatureTimestamp()));

        for (Temperature temp : temperatures) {
            if (temp.getTemperatureSensorValue() > 40) {
                // Start a new "on" period if not already started
                if (stoveStart == null) {
                    stoveStart = temp.getTemperatureTimestamp();
                    System.out.println("Stove ON: " + stoveStart);
                }
            } else {
                // End the "on" period if it was started
                if (stoveStart != null) {
                    LocalDateTime stoveEnd = temp.getTemperatureTimestamp();
                    if (stoveEnd.isAfter(stoveStart)) {
                        stoveDurations.add(new StoveUsage(stoveStart, stoveEnd));
                        System.out.println("Stove OFF: " + stoveEnd + ", Duration: " +
                                Duration.between(stoveStart, stoveEnd).toMinutes() + " minutes");
                    }
                    stoveStart = null;
                }
            }
        }

        // Handle the case where the stove is still on at the end of the data
        if (stoveStart != null) {
            LocalDateTime stoveEnd = temperatures.get(temperatures.size() - 1).getTemperatureTimestamp();
            if (stoveEnd.isAfter(stoveStart)) {
                stoveDurations.add(new StoveUsage(stoveStart, stoveEnd));
                System.out.println("Stove still ON at the end: " + stoveEnd);
            }
        }

        // Keep only the last 10 durations
        int startIndex = Math.max(0, stoveDurations.size() - 10);
        return stoveDurations.subList(startIndex, stoveDurations.size());
    }
}
