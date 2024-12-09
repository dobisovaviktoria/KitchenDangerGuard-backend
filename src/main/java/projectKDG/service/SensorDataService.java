package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectKDG.domain.SensorData;
import projectKDG.repository.SensorDataRepository;
import projectKDG.service.processors.CompositeDataProcessor;
import projectKDG.service.processors.MotionDataProcessor;
import projectKDG.service.processors.TemperatureDataProcessor;

import java.util.List;

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
}
