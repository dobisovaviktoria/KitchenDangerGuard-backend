package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectKDG.domain.Temperature;

import java.util.Optional;

public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {
    Optional<Temperature> findTemperatureByTemperatureSensorId(Integer temperatureSensorId);
}
