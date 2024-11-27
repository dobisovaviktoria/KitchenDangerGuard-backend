package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Sends an email with the specified recipient, subject, and body.
     *
     * @param toEmail the recipient's email address
     * @param subject the email subject
     * @param body    the email content
     */
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kitchendangerguard@gmail.com"); // Update with your sender email
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        try {
            emailSender.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MailException e) {
            System.err.println("Error while sending email: " + e.getMessage());
            throw e; // Optional: Rethrow the exception to handle it in the calling method
        }
    }

    /**
     * Sends a test email to verify the email service is functional.
     *
     * @param recipientEmail the recipient's email address
     */
    public void sendTestEmail(String recipientEmail) {
        String subject = "Test Email from Kitchen Danger Guard";
        String body = "This is a test email to verify email functionality in the Kitchen Danger Guard application.";
        sendEmail(recipientEmail, subject, body);
    }
}
