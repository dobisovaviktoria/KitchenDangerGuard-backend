package projectKDG.service.notification;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import projectKDG.domain.NotificationPreference;

@Component
public class NotificationStrategyFactory {
    private final JavaMailSender mailSender;

    public NotificationStrategyFactory(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public NotificationStrategy createStrategy(NotificationPreference preference) {
        switch (preference) {
            case EMAIL:
                return new EmailNotifier(mailSender);
            case SMS:
                return new SmsNotifier();  // Replace with appropriate sender dependency if needed
            case WEBSITE:
                return new WebNotifier(); // Replace with appropriate sender dependency if needed
            default:
                throw new IllegalArgumentException("Unknown notification preference: " + preference);
        }
    }
}
