package projectKDG.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projectKDG.domain.SensorData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SensorDataRepository extends JpaRepository<SensorData, Integer> {
    @Query("SELECT s FROM SensorData s WHERE s.timestamp >= :fromTime")
    List<SensorData> findRecentSensorData(LocalDateTime fromTime);

    @Query("SELECT AVG(s.temperatureValue) FROM SensorData s WHERE s.timestamp >= :fromTime " +
            "AND s.temperatureValue IS NOT NULL\n")
    Double findAverageTemperature(LocalDateTime fromTime);

    @Query("SELECT CASE WHEN COUNT(s) = 0 THEN true ELSE false END " +
            "FROM SensorData s WHERE s.timestamp >= :fromTime AND s.motionStatus = true")
    Boolean areAllMotionStatusesFalse(LocalDateTime fromTime);

    @Query("SELECT s FROM SensorData s " +
            "JOIN s.arduinoDevice a " +
            "WHERE a.user.userID = :userId")
    List<SensorData> findByUserId(@Param("userId") int userId);

    @Query("""
    SELECT 
        CAST(EXTRACT(HOUR FROM s.timestamp) AS INTEGER) AS hour, 
        COUNT(s.id) AS recordCount
    FROM SensorData s
    JOIN s.arduinoDevice a
    WHERE DATE(s.timestamp) = :selectedDate 
      AND s.temperatureValue > 20
      AND a.user.userID = :userId
    GROUP BY CAST(EXTRACT(HOUR FROM s.timestamp) AS INTEGER)
    ORDER BY CAST(EXTRACT(HOUR FROM s.timestamp) AS INTEGER)
""")
    List<Object[]> findStoveOnDurationsPerHour(
            @Param("selectedDate") LocalDate selectedDate,
            @Param("userId") int userId
    );

    @Query("""
    SELECT 
        CAST(EXTRACT(HOUR FROM s.timestamp) AS INTEGER) AS hour, 
        AVG(COUNT(s.id)) OVER (PARTITION BY CAST(EXTRACT(HOUR FROM s.timestamp) AS INTEGER)) AS avgUsage
    FROM SensorData s
    JOIN s.arduinoDevice a
    WHERE EXTRACT(MONTH FROM s.timestamp) = :month
      AND EXTRACT(YEAR FROM s.timestamp) = :year
      AND s.temperatureValue > 20
      AND a.user.userID = :userId
    GROUP BY CAST(EXTRACT(HOUR FROM s.timestamp) AS INTEGER)
    ORDER BY CAST(EXTRACT(HOUR FROM s.timestamp) AS INTEGER)
    """)
    List<Object[]> findMonthlyStoveDurationsPerHour(
            @Param("month") int month,
            @Param("year") int year,
            @Param("userId") int userId
    );
}
