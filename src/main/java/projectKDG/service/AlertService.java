package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import projectKDG.domain.Motion;
import projectKDG.domain.Temperature;
import projectKDG.repository.MotionRepository;
import projectKDG.repository.TemperatureRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class AlertService {

    @Autowired
    private EmailService emailService;
    private MotionRepository motionRepository;
    private TemperatureRepository temperatureRepository;

    public AlertService(EmailService emailService, MotionRepository motionRepository, TemperatureRepository temperatureRepository) {
        this.emailService = emailService;
        this.motionRepository = motionRepository;
        this.temperatureRepository = temperatureRepository;
    }

    private final int offTemperature = 40;  // Can be replaced with @Value for configurable property
    private final int everyMinute = 10;    // Can be replaced with @Value for configurable property

    public void checkForAlert() {
        Motion latestMotion = motionRepository.findLastMotion();
        Temperature latestTemperature = temperatureRepository.findLastTemperature();
        if (latestMotion != null && latestTemperature != null) {
            // Check if there's no motion and the temperature is above the threshold
            if (!latestMotion.isMotionSensorStatus() &&
                    latestTemperature.getTemperatureSensorValue() > offTemperature) {

                // Calculate time since last motion detected
                long durationInMinutes = ChronoUnit.MINUTES.between(
                        latestMotion.getMotionTimestamp(), LocalDateTime.now());

                // Trigger alert if unattended duration exceeds threshold
                if (durationInMinutes >= everyMinute) {
                    sendAlertEmail(latestMotion, latestTemperature, durationInMinutes);
                }
            }
        }
    }

    private void sendAlertEmail(Motion latestMotion, Temperature latestTemperature, long durationInMinutes) {
        String recipientEmail = "dobisovav@gmail.com"; // Replace with recipient's email
        String subject = "Kitchen Alert: Unattended Stove";
        String message = String.format(
                "Alert! The stove has been running (Temperature: %.2f°C), but no motion has been detected " +
                        "in the kitchen for the last %d minutes. Last motion detected at: %s.",
                latestTemperature.getTemperatureSensorValue(),
                durationInMinutes,
                latestMotion.getMotionTimestamp());

        try {
            emailService.sendEmail(recipientEmail, subject, message);
            System.out.println("Alert email sent successfully!");
        } catch (MailException e) {
            System.err.println("Error while sending alert email: " + e.getMessage());
        }
    }
}

