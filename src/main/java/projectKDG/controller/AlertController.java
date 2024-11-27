package projectKDG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectKDG.domain.Motion;
import projectKDG.domain.Temperature;
import projectKDG.service.AlertService;
import projectKDG.service.EmailService;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    @Autowired
    private AlertService alertService;

    @Autowired
    private EmailService emailService;

    // Endpoint to test email functionality
    @GetMapping("/test-email")
    public ResponseEntity<String> sendTestEmail(@RequestParam String email) {
        try {
            emailService.sendEmail(email, "Test Email", "This is a test email from Kitchen Danger Guard.");
            return ResponseEntity.ok("Test email sent successfully to " + email);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending test email: " + e.getMessage());
        }
    }

    // Endpoint to trigger alert manually
    @GetMapping("/check-alert")
    public ResponseEntity<String> triggerAlertCheck() {
        try {
            alertService.checkForAlert();
            return ResponseEntity.ok("Alert check executed.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing alert: " + e.getMessage());
        }
    }

    // DTO for receiving Motion and Temperature data in requests
    static class AlertRequest {
        private Motion motion;
        private Temperature temperature;

        // Getters and Setters
        public Motion getMotion() {
            return motion;
        }

        public void setMotion(Motion motion) {
            this.motion = motion;
        }

        public Temperature getTemperature() {
            return temperature;
        }

        public void setTemperature(Temperature temperature) {
            this.temperature = temperature;
        }
    }
}
