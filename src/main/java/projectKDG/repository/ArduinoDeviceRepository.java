package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectKDG.domain.ArduinoDevice;

public interface ArduinoDeviceRepository extends JpaRepository<ArduinoDevice, Integer> {
}
