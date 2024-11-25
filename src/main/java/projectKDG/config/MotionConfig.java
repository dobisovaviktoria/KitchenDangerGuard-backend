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

            // Generate 100 randomized motion data points
            List<Motion> motions = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                boolean motionDetected = random.nextBoolean(); // Random true/false
                LocalDateTime randomDateTime = LocalDateTime.now()
                        .minusDays(random.nextInt(365)) // Random day within the last year
                        .minusHours(random.nextInt(24)) // Random hour of the day
                        .minusMinutes(random.nextInt(60)); // Random minute
                motions.add(new Motion(motionDetected, randomDateTime));
            }
            repository.saveAll(motions);

            // Generate 100 randomized temperature data points
            List<Temperature> temperatures = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                float randomTemp = 20.0F + random.nextFloat() * 30.0F; // Random temp between 20.0 and 50.0
                LocalDateTime randomDateTime = LocalDateTime.now()
                        .minusDays(random.nextInt(365)) // Random day within the last year
                        .minusHours(random.nextInt(24)) // Random hour of the day
                        .minusMinutes(random.nextInt(60)); // Random minute
                temperatures.add(new Temperature(randomTemp, randomDateTime));
            }
            temperatureRepository.saveAll(temperatures);
        };
    }
}
