package projectKDG.domain;

import java.time.LocalDate;

public class Alert {
    private int alertID;
    private AlertMessage alertMessage;
    private LocalDate timestamp;
    private NotificationPreference notificationPreference;

    public Alert(int alertID, AlertMessage alertMessage, LocalDate timestamp, NotificationPreference notificationPreference) {
        this.alertID = alertID;
        this.alertMessage = alertMessage;
        this.timestamp = timestamp;
        this.notificationPreference = notificationPreference;
    }

    public int getAlertID() {
        return alertID;
    }

    public AlertMessage getAlertMessage() {
        return alertMessage;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public NotificationPreference getNotificationPreference() {
        return notificationPreference;
    }
}
