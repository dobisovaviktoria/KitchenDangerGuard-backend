package projectKDG.service.notification;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotifier implements NotificationStrategy {
    private final JavaMailSender emailSender;

    public EmailNotifier(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void notify(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kitchendangerguard@gmail.com"); // Update with your sender email
        message.setTo(notification.getDestination());
        message.setSubject("Kitchen Danger Guard Notification");
        message.setText(notification.getMessage());

        try {
            emailSender.send(message);
            System.out.println("Email sent successfully to " + notification.getDestination());
        } catch (MailException e) {
            System.err.println("Error while sending email: " + e.getMessage());
            throw e; // Optional: Rethrow the exception to handle it in the calling method
        }
    }
}