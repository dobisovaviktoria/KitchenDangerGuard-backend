package projectKDG.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import projectKDG.domain.Motion;
import projectKDG.domain.NotificationPreference;
import projectKDG.domain.Temperature;
import projectKDG.repository.MotionRepository;
import projectKDG.repository.TemperatureRepository;
import projectKDG.service.notification.*;

@Service
public class AlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertService.class);

    private final MotionRepository motionRepository;
    private final TemperatureRepository temperatureRepository;
    private final NotificationContext notificationContext;
    private final NotificationPreference userNotificationPreference;
    private final String userNotificationDestination;

    private final int offTemperature = 40;  // Configurable threshold
    private final int everyMinute = 10;    // Configurable duration

    // Constructor injection
    public AlertService(
            MotionRepository motionRepository,
            TemperatureRepository temperatureRepository,
            NotificationContext notificationContext,
            @Value("${kdg.notification-preference}") NotificationPreference userNotificationPreference,
            @Value("${kdg.notification-destination}") String userNotificationDestination) {
        this.motionRepository = motionRepository;
        this.temperatureRepository = temperatureRepository;
        this.notificationContext = notificationContext;
        this.userNotificationPreference = userNotificationPreference;
        this.userNotificationDestination = userNotificationDestination;
    }

    @Scheduled(fixedRate = 5000)
    public void checkForAlert() {
        Motion latestMotion = motionRepository.findLastMotion();
        Temperature latestTemperature = temperatureRepository.findLastTemperature();
        if (latestMotion != null && latestTemperature != null) {
            if (!latestMotion.isMotionSensorStatus() &&
                    latestTemperature.getTemperatureSensorValue() > offTemperature) {
                log.info("Sending alert notification");
                notificationContext.executeStrategy(
                        new Notification(userNotificationDestination,
                                "KDG Alert. Temperature value is: " + latestTemperature.getTemperatureSensorValue()),
                        userNotificationPreference);
            }
        }
    }
}