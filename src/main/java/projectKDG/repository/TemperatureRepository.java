package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectKDG.domain.Temperature;

public interface TemperatureRepository extends JpaRepository<Temperature, Integer> {
}
