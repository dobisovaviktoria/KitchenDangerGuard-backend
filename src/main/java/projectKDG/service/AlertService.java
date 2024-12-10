package projectKDG.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import projectKDG.domain.ArduinoDevice;
import projectKDG.domain.NotificationPreference;
import projectKDG.domain.SensorData;
import projectKDG.domain.User;
import projectKDG.repository.SensorDataRepository;
import projectKDG.service.notification.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertService.class);

    //    private final MotionRepository motionRepository;
//    private final TemperatureRepository temperatureRepository;
    private final SensorDataRepository sensorDataRepository;
    private final NotificationContext notificationContext;
    //NotificationStrategy notificationStrategy;
    private final NotificationPreference userNotificationPreference;
    private final String userNotificationDestination;

    private final int offTemperature = 40;  // Configurable threshold
    private final int everyMinute = 10;    // Configurable duration
    private final NotificationTrackerService notificationTrackerService;

    // Constructor injection
    public AlertService(
            SensorDataRepository sensorDataRepository,
            //@Qualifier("compositeNotificationStrategy") NotificationStrategy notificationStrategy,
            NotificationContext notificationContext,
            @Value("${kdg.notification-preference}") NotificationPreference userNotificationPreference,
            @Value("${kdg.notification-destination}") String userNotificationDestination, NotificationTrackerService notificationTrackerService) {
        this.sensorDataRepository = sensorDataRepository;
        this.notificationContext = notificationContext;
        this.userNotificationPreference = userNotificationPreference;
        this.userNotificationDestination = userNotificationDestination;
        this.notificationTrackerService = notificationTrackerService;
    }

    @Scheduled(fixedRate = 10000)
    public void checkForAlert() {
        LocalDateTime time = LocalDateTime.now().minusMinutes(10);
        List<SensorData> latestSensorData = sensorDataRepository.findRecentSensorData(time);
        if (latestSensorData != null) {
            if (sensorDataRepository.areAllMotionStatusesFalse(time) && sensorDataRepository.findAverageTemperature(time) != null &&
                    sensorDataRepository.findAverageTemperature(time) > offTemperature) {
                log.info("Sending alert notification");
//From the data , we know device id and then the user ... to send an alert.
                for (SensorData data : latestSensorData) {
                    ArduinoDevice arduinoDevice = data.getArduinoDevice(); // Fetch the ArduinoDevice
                    if (arduinoDevice != null) {
                        User user = arduinoDevice.getUser(); // Fetch the User from ArduinoDevice
                        if (user != null) {
                            int userId = user.getUserID(); // Extract the userId

                            // Create a notification record
                            notificationTrackerService.createNotification(userId);

                            notificationContext.executeStrategy(
                                    new Notification(userNotificationDestination,
                                            "KDG Alert. Stove is unattended and average temperature value is: " +
                                                    sensorDataRepository.findAverageTemperature(time)), userNotificationPreference);

                        }
                    }
                }
            }
        }
    }
}

