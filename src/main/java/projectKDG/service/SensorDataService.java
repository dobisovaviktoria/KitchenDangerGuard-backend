package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectKDG.domain.SensorData;
import projectKDG.repository.SensorDataRepository;

import java.util.List;

@Service
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;

    @Autowired
    public SensorDataService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    // Save sensor data (motion + temperature)
    public void saveSensorData(SensorData sensorData) {
        if (sensorData != null) {
            sensorDataRepository.save(sensorData);
        }
    }

    // Get all sensor data
    public List<SensorData> getAllSensorData() {
        return sensorDataRepository.findAll();
    }
}
