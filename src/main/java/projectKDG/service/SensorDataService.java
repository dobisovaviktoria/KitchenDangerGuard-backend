package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectKDG.domain.SensorData;
import projectKDG.repository.SensorDataRepository;
import projectKDG.service.processors.CompositeDataProcessor;
import projectKDG.service.processors.MotionDataProcessor;
import projectKDG.service.processors.TemperatureDataProcessor;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;
    private final CompositeDataProcessor compositeDataProcessor;

    @Autowired
    public SensorDataService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
        this.compositeDataProcessor = new CompositeDataProcessor();

        // Add specific processors to the composite
        this.compositeDataProcessor.addProcessor(new MotionDataProcessor());
        this.compositeDataProcessor.addProcessor(new TemperatureDataProcessor());

    }

    // Save sensor data (motion + temperature)
    public void saveSensorData(SensorData sensorData) {
        if (sensorData != null) {
            // Process the sensor data before saving
            compositeDataProcessor.process(sensorData);
            // saving
            sensorDataRepository.save(sensorData);
        }
    }

    // Get all sensor data
    public List<SensorData> getAllSensorData() {
        return sensorDataRepository.findAll();
    }

    public List<SensorData> getSensorDataByUserId(int userId) {
        return sensorDataRepository.findByUserId(userId);
    }

    public Map<String, Integer> getStoveOnDurationsPerHour(LocalDate selectedDate, int userId) {
        List<Object[]> queryResults = sensorDataRepository.findStoveOnDurationsPerHour(selectedDate, userId);
        Map<String, Integer> hourlyDurations = new LinkedHashMap<>();

        // Initialize all hours with 0 duration
        for (int i = 0; i < 24; i++) {
            String hour = String.format("%02d:00", i);
            hourlyDurations.put(hour, 0);
        }

        // Populate durations from query results
        for (Object[] row : queryResults) {
            Integer hour = (Integer) row[0];
            Integer duration = ((Number) row[1]).intValue();

            String formattedHour = String.format("%02d:00", hour);
            hourlyDurations.put(formattedHour, duration);
        }

        return hourlyDurations;
    }

    public Map<String, Double> getMonthlyStoveUsageAverages(int month, int year, int userId) {
        List<Object[]> queryResults = sensorDataRepository.findMonthlyStoveDurationsPerHour(month, year, userId);
        Map<String, Double> hourlyAverages = new LinkedHashMap<>();

        // Initialize all hours with 0 average
        for (int i = 0; i < 24; i++) {
            String hour = String.format("%02d:00", i);
            hourlyAverages.put(hour, 0.0);
        }

        // Populate averages from query results
        for (Object[] row : queryResults) {
            Integer hour = (Integer) row[0];
            Double avgUsage = (Double) row[1];

            String formattedHour = String.format("%02d:00", hour);
            hourlyAverages.put(formattedHour, avgUsage);
        }

        return hourlyAverages;
    }
}
