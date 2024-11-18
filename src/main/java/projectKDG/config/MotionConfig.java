package projectKDG.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import projectKDG.domain.Motion;
import projectKDG.domain.Temperature;
import projectKDG.repository.MotionRepository;
import projectKDG.repository.TemperatureRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
public class MotionConfig {
    String dateString = "2000-10-23 14:30:00";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

    @Bean
    CommandLineRunner commandLineRunner(MotionRepository repository, TemperatureRepository temperatureRepository) {
        return args -> {
            Motion eenie = new Motion(true, dateTime);
            Motion meenie= new Motion(false, dateTime);
            repository.saveAll(List.of(eenie, meenie));

            Temperature minie = new Temperature(47.7F, dateTime);
            Temperature moo = new Temperature(48.8F, dateTime);
            temperatureRepository.saveAll(List.of(minie, moo));

        };
    }
}
