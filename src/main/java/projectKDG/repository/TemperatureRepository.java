package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectKDG.domain.Temperature;

import java.util.List;
import java.util.Optional;

public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {
    Optional<Temperature> findTemperatureByTemperatureSensorId(Integer temperatureSensorId);

    @Query("SELECT t FROM Temperature t ORDER BY t.temperatureTimestamp DESC LIMIT 20")
    List<Temperature> findLatestTemperatures();
}
