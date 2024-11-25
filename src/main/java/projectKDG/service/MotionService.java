package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import projectKDG.domain.Motion;
import projectKDG.domain.Temperature;
import projectKDG.repository.MotionRepository;
import projectKDG.repository.TemperatureRepository;

import java.util.List;

@Service
public class MotionService {

    private final MotionRepository motionRepository;
    private final TemperatureRepository temperatureRepository;
    private final double thresholdTemperature = 40;

    @Autowired
    public MotionService(MotionRepository motionRepository, TemperatureRepository temperatureRepository) {
        this.motionRepository = motionRepository;
        this.temperatureRepository = temperatureRepository;
    }

    @GetMapping
    public List<Motion> getMotions() {
        return motionRepository.findAll();
    }

    public void addNewMotion(Motion motion) {
        Temperature currentTemperature = temperatureRepository.findLastTemperature();

        if (currentTemperature != null && currentTemperature.getTemperatureSensorValue() > thresholdTemperature){
            motionRepository.findMotionByMotionSensorId(motion.getMotionSensorId());
            motionRepository.save(motion);
            System.out.println("Motion data saved: " + motion);
            System.out.println(motion);
        }
    }

    public List<Motion> getLatestMotions() {
        return motionRepository.findLatestMotions();
    }

}