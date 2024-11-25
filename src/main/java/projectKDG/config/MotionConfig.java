package projectKDG.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projectKDG.domain.Motion;
import projectKDG.domain.Temperature;
import projectKDG.repository.MotionRepository;
import projectKDG.repository.TemperatureRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class MotionConfig {

    @Bean
    CommandLineRunner commandLineRunner(MotionRepository repository, TemperatureRepository temperatureRepository) {
        return args -> {
            // Initialize random number generator
            Random random = new Random();

            // Generate 100 randomized motion data points with unique timestamps
            List<Motion> motions = new ArrayList<>();
            LocalDateTime baseTime = LocalDateTime.now(); // Use a base time for uniqueness
            for (int i = 0; i < 100; i++) {
                boolean motionDetected = random.nextBoolean(); // Random true/false
                LocalDateTime uniqueTimestamp = baseTime
                        .minusDays(random.nextInt(365)) // Random day within the last year
                        .minusHours(random.nextInt(24)) // Random hour of the day
                        .minusMinutes(random.nextInt(60)) // Random minute
                        .plusSeconds(i); // Ensure unique timestamp by adding seconds
                motions.add(new Motion(motionDetected, uniqueTimestamp));
            }
            repository.saveAll(motions);

            // Generate 100 randomized temperature data points with unique timestamps
            List<Temperature> temperatures = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                float randomTemp = 20.0F + random.nextFloat() * 30.0F; // Random temp between 20.0 and 50.0
                LocalDateTime uniqueTimestamp = baseTime
                        .minusDays(random.nextInt(365)) // Random day within the last year
                        .minusHours(random.nextInt(24)) // Random hour of the day
                        .minusMinutes(random.nextInt(60)) // Random minute
                        .plusSeconds(i + 100); // Offset by 100 seconds to avoid overlap with motion data
                temperatures.add(new Temperature(randomTemp, uniqueTimestamp));
            }
            temperatureRepository.saveAll(temperatures);
        };
    }
}
