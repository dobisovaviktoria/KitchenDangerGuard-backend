package projectKDG.domain;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_tracker")
public class NotificationTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_sequence")
    @SequenceGenerator(name = "notification_sequence", sequenceName = "sequence_notification", allocationSize = 1)
    @Column(name = "noti_id")
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;

    public NotificationTracker() {
    }



    public NotificationTracker(User user, LocalDateTime now) {
        this.user = user;
        this.sentAt = now;
    }

    // Getters and Setters
    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
