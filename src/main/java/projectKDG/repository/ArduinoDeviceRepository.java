package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectKDG.domain.ArduinoDevice;

import java.util.Optional;

public interface ArduinoDeviceRepository extends JpaRepository<ArduinoDevice, Integer> {
    Optional<ArduinoDevice> findByUser_UserID(int userId);
}
