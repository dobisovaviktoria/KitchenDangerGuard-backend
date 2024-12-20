package projectKDG.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import projectKDG.domain.ArduinoDevice;
import projectKDG.domain.SensorData;
import projectKDG.domain.User;
import projectKDG.repository.SensorDataRepository;
import projectKDG.service.notification.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertService.class);

    private final SensorDataRepository sensorDataRepository;
    private final NotificationContext notificationContext;
    //NotificationStrategy notificationStrategy;

    private final int offTemperature = 40;  // Configurable threshold
    private final int everyMinute = 10;    // Configurable duration
    private final NotificationTrackerService notificationTrackerService;

    // Constructor injection
    public AlertService(
            SensorDataRepository sensorDataRepository,
            //@Qualifier("compositeNotificationStrategy") NotificationStrategy notificationStrategy,
            NotificationContext notificationContext,
            NotificationTrackerService notificationTrackerService) {
        this.sensorDataRepository = sensorDataRepository;
        this.notificationContext = notificationContext;
        this.notificationTrackerService = notificationTrackerService;
    }

    @Scheduled(fixedRate = 10000)
    @Scheduled(fixedRate = 10000)
    public void checkForAlert() {
        LocalDateTime time = LocalDateTime.now().minusMinutes(everyMinute);
        List<SensorData> latestSensorData = sensorDataRepository.findRecentSensorData(time);

        if (latestSensorData != null && !latestSensorData.isEmpty()) {
            boolean allMotionFalse = sensorDataRepository.areAllMotionStatusesFalse(time);
            Double avgTemp = sensorDataRepository.findAverageTemperature(time);

            log.info("All motion statuses false: {}", allMotionFalse);
            log.info("Average temperature: {}", avgTemp);

            if (avgTemp == null) {
                log.warn("Average temperature is null.");
                return;
            }

            if (avgTemp > offTemperature) {
                log.info("Triggering alerts for devices with average temperature > {}", offTemperature);

                for (SensorData data : latestSensorData) {
                    ArduinoDevice arduinoDevice = data.getArduinoDevice();
                    log.info("arduino device id is {}", arduinoDevice.getArduinoDeviceId());

                    User user = arduinoDevice.getUser();

                    if (user == null) {
                        log.warn("No user associated with ArduinoDevice ID: {}", arduinoDevice.getArduinoDeviceId());
                        continue;
                    }

                    String email = user.getEmail();
                    if (email == null || email.isEmpty()) {
                        log.warn("User email is not available for User ID: {}", user.getUserID());
                        continue;
                    }

                    // Create a notification record
                    notificationTrackerService.createNotification(user.getUserID());

                    // Send the notification
                    String message = "KDG Alert. Stove is unattended and average temperature value is: " + avgTemp;
                    Notification notification = new Notification(email, message);
                    notificationContext.executeStrategy(notification, user.getNotificationPreference());

                    log.info("Notification sent to user: {} (Email: {})", user.getUserName(), email);
                }
            }
        } else {
            log.info("No recent sensor data found since {}", time);
        }
    }
}