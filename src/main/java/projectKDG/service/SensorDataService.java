package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectKDG.domain.Motion;
import projectKDG.domain.Temperature;
import projectKDG.repository.MotionRepository;
import projectKDG.repository.TemperatureRepository;

import java.util.List;

@Service
public class SensorDataService {

    private final MotionRepository motionRepository;
    private final TemperatureRepository temperatureRepository;

    @Autowired
    public SensorDataService(MotionRepository motionRepository, TemperatureRepository temperatureRepository) {
        this.motionRepository = motionRepository;
        this.temperatureRepository = temperatureRepository;
    }

    // Save motion data
    public void saveMotionData(Boolean motionStatus) {
        if (motionStatus != null) {
            Motion motion = new Motion();
            motion.setMotionSensorStatus(motionStatus);
            motion.setMotionTimestamp(java.time.LocalDateTime.now());
            motionRepository.save(motion);
        }
    }

    // Save temperature data
    public void saveTemperatureData(Float temperatureValue) {
        if (temperatureValue != null) {
            Temperature temperature = new Temperature();
            temperature.setTemperatureSensorValue(temperatureValue);
            temperature.setTemperatureTimestamp(java.time.LocalDateTime.now());
            temperatureRepository.save(temperature);
        }
    }

    // Get all motion data
    public List<Motion> getAllMotions() {
        return motionRepository.findAll();
    }

    // Get all temperature data
    public List<Temperature> getAllTemperatures() {
        return temperatureRepository.findAll();
    }
}
