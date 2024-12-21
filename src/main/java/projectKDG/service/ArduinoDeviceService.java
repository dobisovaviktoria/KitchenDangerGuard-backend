package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectKDG.domain.ArduinoDevice;
import projectKDG.repository.ArduinoDeviceRepository;

@Service
public class ArduinoDeviceService {
    private final ArduinoDeviceRepository arduinoDeviceRepository;

    @Autowired
    public ArduinoDeviceService(ArduinoDeviceRepository arduinoDeviceRepository) {
        this.arduinoDeviceRepository = arduinoDeviceRepository;
    }

    public ArduinoDevice getArduinoDeviceById(int id) {
        // Use findById and handle the Optional
        return arduinoDeviceRepository.findById(id).orElse(null);
    }

    public ArduinoDevice getArduinoDeviceByUserId(int userId) {
        return arduinoDeviceRepository.findByUser_UserID(userId).orElse(null);
    }

    public ArduinoDevice findById(int arduinoDeviceID) {
        return arduinoDeviceRepository.findById(arduinoDeviceID).orElse(null);
    }

    public void save(ArduinoDevice arduinoDevice) {
        arduinoDeviceRepository.save(arduinoDevice);
    }
}