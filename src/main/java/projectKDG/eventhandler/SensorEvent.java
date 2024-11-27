package projectKDG.eventhandler;

import java.time.LocalDateTime;

public interface SensorEvent {
    String getSensorType(); // E.g., "MOTION", "TEMPERATURE", etc.
    LocalDateTime getTimestamp();
}

