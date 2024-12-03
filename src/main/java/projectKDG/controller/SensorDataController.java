package projectKDG.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectKDG.controller.model.SensorDataDTO;
import projectKDG.domain.SensorData;
import projectKDG.service.SensorDataService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
public class SensorDataController {

    private static final Logger log = LoggerFactory.getLogger(SensorDataController.class);
    private final SensorDataService sensorDataService;

    @Autowired
    public SensorDataController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    // GET endpoint to retrieve all sensor data records
    @GetMapping("/sensor-data")
    public List<SensorData> getAllSensorData() {
        log.info("Fetching all sensor data...");
        return sensorDataService.getAllSensorData();
    }

    // POST endpoint to receive sensor data from Arduino
    @PostMapping("/sensor-data")
    public ResponseEntity<String> receiveSensorData(@RequestBody SensorDataDTO sensorDataDto) {
        LocalDateTime now = LocalDateTime.now();
        log.info("Received sensor data - Motion: {}, Temperature: {} at {}",
                sensorDataDto.getMotionStatus(),
                sensorDataDto.getTemperatureValue(),
                now);

        // Save sensor data to the database
        SensorData sensorData = new SensorData();
        sensorData.setMotionStatus(sensorDataDto.getMotionStatus());
        sensorData.setTemperatureValue(sensorDataDto.getTemperatureValue());
        sensorData.setTimestamp(now);

        sensorDataService.saveSensorData(sensorData);

        // Return a response back to Arduino
        return new ResponseEntity<>(
                String.format("Data received - Motion: %s, Temperature: %.2f",
                        sensorData.getMotionStatus() ? "Detected" : "Not Detected",
                        sensorData.getTemperatureValue()),
                HttpStatus.CREATED
        );
    }
}
