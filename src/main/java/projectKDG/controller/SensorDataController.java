package projectKDG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectKDG.controller.model.SensorDataDTO;
import projectKDG.service.SensorDataService;

@Controller
@RequestMapping("/sensor-data")
public class SensorDataController {

    private final SensorDataService sensorDataService;

    @Autowired
    public SensorDataController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> receiveSensorData(@RequestBody SensorDataDTO sensorDataDTO) {
        // Save motion data if present
        sensorDataService.saveMotionData(sensorDataDTO.getMotionStatus());

        // Save temperature data if present
        sensorDataService.saveTemperatureData(sensorDataDTO.getTemperatureValue());

        return new ResponseEntity<>("Data received successfully from sensorController", HttpStatus.CREATED);
    }

    @GetMapping("/motion")
    @ResponseBody
    public ResponseEntity<?> getAllMotionData() {
        return ResponseEntity.ok(sensorDataService.getAllMotions());
    }

    @GetMapping("/temperature")
    @ResponseBody
    public ResponseEntity<?> getAllTemperatureData() {
        return ResponseEntity.ok(sensorDataService.getAllTemperatures());
    }
}
