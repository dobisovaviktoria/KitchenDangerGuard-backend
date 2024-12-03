//package projectKDG.config;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import projectKDG.domain.SensorData;
//import projectKDG.repository.SensorDataRepository;
//
//import java.time.LocalDateTime;
//import java.util.Random;
//
//@Configuration
//public class SensorDataSeederConfig {
//
//    @Bean
//    public CommandLineRunner seedSensorData(SensorDataRepository sensorDataRepository) {
//        return args -> {
//            // Parameters for data generation
//            int numberOfRecords = 500; // Total records to generate
//            int daysBack = 7; // Spread timestamps over the past 'daysBack' days
//
//            Random random = new Random();
//
//            for (int i = 1; i < numberOfRecords; i++) {
//                // Generate random motion status (true/false)
//                Boolean motionStatus = random.nextBoolean();
//
//                // Generate random temperature (e.g., between 15.0 and 30.0)
//                Float temperatureValue = 15.0f + random.nextFloat() * 15.0f;
//
//                // Generate random timestamp within the past 'daysBack' days
//                LocalDateTime timestamp = LocalDateTime.now().minusDays(random.nextInt(daysBack))
//                        .minusHours(random.nextInt(24))
//                        .minusMinutes(random.nextInt(60))
//                        .minusSeconds(random.nextInt(60));
//
//                // Create a new SensorData object
//                SensorData sensorData = new SensorData(motionStatus, temperatureValue, timestamp);
//
//                // Save to the database using the repository
//                sensorDataRepository.save(sensorData);
//            }
//
//            System.out.println("Sensor data seeded successfully!");
//        };
//    }
//}
