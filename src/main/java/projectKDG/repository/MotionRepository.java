package projectKDG.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectKDG.domain.Motion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotionRepository
        extends JpaRepository<Motion, Integer> {

    Optional<Motion> findMotionByMotionSensorId(Integer motionSensorId);

    @Query("SELECT m FROM Motion m ORDER BY m.motionTimestamp DESC LIMIT 10")
    List<Motion> findLatestMotions();
}