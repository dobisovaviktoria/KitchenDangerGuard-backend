package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import projectKDG.domain.Temperature;
import projectKDG.repository.TemperatureRepository;

import java.util.List;

@Service
public class TemperatureService{
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
        temperatureRepository.save(temperature);
        System.out.println("Motion data saved: " + temperature);
        System.out.println(temperature);
    }
}
