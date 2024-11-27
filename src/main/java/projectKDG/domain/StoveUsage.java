package projectKDG.domain;

import java.time.LocalDateTime;
import java.time.Duration;

public class StoveUsage {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public StoveUsage(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Duration getDuration() {
        return Duration.between(startTime, endTime);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Stove Usage: " + startTime + " to " + endTime + " (Duration: " + getDuration().toMinutes() + " minutes)";
    }
}
