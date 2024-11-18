package projectKDG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectKDG.controller.model.TemperatureDTO;
import projectKDG.domain.Temperature;
import projectKDG.service.TemperatureService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TemperatureController {

    private final TemperatureService temperatureService;

    @Autowired
    public TemperatureController(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    // GET endpoint to retrieve all motion records
    @GetMapping("/temperature")
    @ResponseBody
    public List<Temperature> getTemperatures() {
        return temperatureService.getTemperatures();
    }

    // POST endpoint to receive motion data from Arduino
    @PostMapping("/temperature")
    @ResponseBody
    public ResponseEntity<String> receiveTemperatureData(@RequestBody TemperatureDTO temperatureDto) {
        // Print the received boolean motion status
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Received temperature data: " + temperatureDto.getTemperatureValue() + " at " + now);

        // Save motion data to the database
        Temperature temperature = new Temperature();
        temperature.setTemperatureSensorValue(temperatureDto.getTemperatureValue());
        temperature.setTemperatureTimestamp(now);
        temperatureService.addNewTemperature(temperature);

        // Return a response back to Arduino
        return new ResponseEntity<>(
                "Data received: " + (temperature.getTemperatureSensorValue()),
                HttpStatus.CREATED
        );
    }
}
