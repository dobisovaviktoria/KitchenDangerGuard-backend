package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectKDG.domain.SensorData;

import java.time.LocalDateTime;
import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {
    @Query("SELECT s FROM SensorData s WHERE s.timestamp >= :fromTime")
    List<SensorData> findRecentSensorData(LocalDateTime fromTime);

    @Query("SELECT AVG(s.temperatureValue) FROM SensorData s WHERE s.timestamp >= :fromTime")
    Double findAverageTemperature(LocalDateTime fromTime);

    @Query("SELECT CASE WHEN COUNT(s) = 0 THEN true ELSE false END " +
            "FROM SensorData s WHERE s.timestamp >= :fromTime AND s.motionStatus = true")
    Boolean areAllMotionStatusesFalse(LocalDateTime fromTime);

    @Query("SELECT s FROM SensorData s " +
            "JOIN s.arduinoDevice a " +
            "WHERE a.user.userID = :userId")
    List<SensorData> findByUserId(@Param("userId") int userId);
}
