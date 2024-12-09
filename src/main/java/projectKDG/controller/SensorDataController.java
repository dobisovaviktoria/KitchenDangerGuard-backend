package projectKDG.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import projectKDG.controller.model.SensorDataDTO;
//import projectKDG.domain.ArduinoDevice;
import projectKDG.domain.ArduinoDevice;
import projectKDG.domain.SensorData;
import projectKDG.repository.ArduinoDeviceRepository;
import projectKDG.service.ArduinoDeviceService;
import projectKDG.service.SensorDataService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class SensorDataController {

    private static final Logger log = LoggerFactory.getLogger(SensorDataController.class);
    private final SensorDataService sensorDataService;
    private final ArduinoDeviceService arduinoDeviceService;

    @Autowired
    public SensorDataController(SensorDataService sensorDataService, ArduinoDeviceService arduinoDeviceService) {
        this.sensorDataService = sensorDataService;
        this.arduinoDeviceService = arduinoDeviceService;
    }

    // GET endpoint to retrieve all sensor data records
    @GetMapping("/sensor-data")
    @ResponseBody
    public List<SensorData> getAllSensorData() {
        log.info("Fetching all sensor data...");
        return sensorDataService.getAllSensorData();
    }

    // POST endpoint to receive sensor data from Arduino
    @PostMapping("/sensor-data")
    @ResponseBody
    public ResponseEntity<String> receiveSensorData(@RequestBody SensorDataDTO sensorDataDto) {
        LocalDateTime now = LocalDateTime.now();
        log.info("Received sensor data - Motion: {}, Temperature: {} at {} from Arduino {}.",
                sensorDataDto.getMotionStatus(),
                sensorDataDto.getTemperatureValue(),
                sensorDataDto.getDeviceId(),
                now);


        // Fetch the ArduinoDevice
        ArduinoDevice arduinoDevice = arduinoDeviceService.getArduinoDeviceById(sensorDataDto.getDeviceId());
        if (arduinoDevice == null) {
            return new ResponseEntity<>(
                    String.format("Arduino device with ID %d not found.", sensorDataDto.getDeviceId()),
                    HttpStatus.NOT_FOUND
            );
        }

        // Save sensor data to the database
        SensorData sensorData = new SensorData();
        sensorData.setMotionStatus(sensorDataDto.getMotionStatus());
        sensorData.setTemperatureValue(sensorDataDto.getTemperatureValue());
        sensorData.setArduinoDevice(arduinoDevice);
        sensorData.setTimestamp(now);

        sensorDataService.saveSensorData(sensorData);


        // Return a response back to Arduino
        return new ResponseEntity<>(
                String.format("Data received - Motion: %s, Temperature: %.2f, Arduino: %d",
                        sensorData.getMotionStatus() ? "Detected" : "Not Detected",
                        sensorData.getTemperatureValue(),
                        sensorData.getArduinoDevice().getArduinoDeviceId()),
                HttpStatus.CREATED
        );
    }
}
