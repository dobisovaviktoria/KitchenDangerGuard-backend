package projectKDG.eventhandler;

import java.time.LocalDateTime;

public class MotionEvent implements SensorEvent {
    private final String motionSensorId;
    private final LocalDateTime timestamp;
    private final String motionSensorStatus;  // New field for status

    public MotionEvent(String motionSensorId, LocalDateTime timestamp, String motionSensorStatus) {
        this.motionSensorId = motionSensorId;
        this.timestamp = timestamp;
        this.motionSensorStatus = motionSensorStatus;  // Initialize the status
    }

    @Override
    public String getSensorType() {
        return "MOTION";
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMotionSensorId() {
        return motionSensorId;
    }

    public String getMotionSensorStatus() {
        return motionSensorStatus;
    }
}
