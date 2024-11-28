package projectKDG.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import projectKDG.domain.Motion;
import projectKDG.domain.Temperature;
import projectKDG.repository.MotionRepository;
import projectKDG.repository.TemperatureRepository;
import projectKDG.service.notification.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class AlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertService.class);

    @Autowired
    private MotionRepository motionRepository;
    private TemperatureRepository temperatureRepository;

    private NotificationContext notificationContext;
    private String userNotificationDestination;

    public AlertService(MotionRepository motionRepository, TemperatureRepository temperatureRepository,
                        @Value("${kdg.notification-preference}") String userNotificationChoice,
                        @Value("${kdg.notification-destination}") String userNotificationDestination,
                        JavaMailSender mailSender) {
        this.motionRepository = motionRepository;
        this.temperatureRepository = temperatureRepository;
        this.userNotificationDestination = userNotificationDestination;
        if ("email".equalsIgnoreCase(userNotificationChoice)) {
            notificationContext = new NotificationContext(new EmailNotifier(mailSender));
        } else if ("sms".equalsIgnoreCase(userNotificationChoice)) {
            notificationContext = new NotificationContext(new SmsNotifier());
        } else {
            notificationContext = new NotificationContext(new WebNotifier());
        }
    }

    private final int offTemperature = 40;  // Can be replaced with @Value for configurable property
    private final int everyMinute = 10;    // Can be replaced with @Value for configurable property

    @Scheduled(fixedRate = 5000)
    public void checkForAlert() {
        Motion latestMotion = motionRepository.findLastMotion();
        Temperature latestTemperature = temperatureRepository.findLastTemperature();
        if (latestMotion != null && latestTemperature != null) {
            // Check if there's no motion and the temperature is above the threshold
            if (!latestMotion.isMotionSensorStatus() &&
                    latestTemperature.getTemperatureSensorValue() > offTemperature) {
                log.info("Sending alert notification");
                notificationContext.executeStrategy(new Notification(userNotificationDestination, "KDG Alert. Temperature value is: " + latestTemperature.getTemperatureSensorValue()));


            }
        }
    }
}