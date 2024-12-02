package projectKDG.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import projectKDG.domain.Temperature;
import projectKDG.repository.TemperatureRepository;

import java.util.List;

@Service
public class TemperatureService{
    private static final Logger log = LoggerFactory.getLogger(TemperatureService.class);
    private final TemperatureRepository temperatureRepository;

    @Autowired
    public TemperatureService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    @GetMapping
    public List<Temperature> getTemperatures() {
        return temperatureRepository.findAll();
    }

    public void addNewTemperature(Temperature temperature) {
        temperatureRepository.findTemperatureByTemperatureSensorId(temperature.getTemperatureSensorId());
        if (temperature.getTemperatureSensorValue() != temperatureRepository.findLastTemperature().getTemperatureSensorValue()){
            temperature.setTemperatureSensorValue(temperature.getTemperatureSensorValue());
            temperatureRepository.save(temperature);
            log.info("Temperature data saved: " + temperature);
        } else {
            log.info("Temperature data saving to data base skipped " + temperature);
        }
    }

    public List<Temperature> getLatestTemperatures() {
        return temperatureRepository.findLatestTemperatures();
    }
}
