package projectKDG.repository;

import org.springframework.stereotype.Repository;
import projectKDG.domain.Motion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface MotionRepository
        extends JpaRepository<Motion, Integer> {

    Optional<Motion> findMotionByMotionSensorId(Integer motionSensorId);

}