package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectKDG.domain.SensorData;

public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {
}
