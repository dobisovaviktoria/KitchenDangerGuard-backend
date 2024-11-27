package projectKDG.eventhandler;

import java.time.LocalDateTime;

public class TemperatureEvent implements SensorEvent {
    private final String temperatureSensorId;
    private final double temperatureValue;
    private final LocalDateTime timestamp;

    public TemperatureEvent(String temperatureSensorId, double temperatureValue, LocalDateTime timestamp) {
        this.temperatureSensorId = temperatureSensorId;
        this.temperatureValue = temperatureValue;
        this.timestamp = timestamp;
    }

    @Override
    public String getSensorType() {
        return "TEMPERATURE";
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getTemperatureValue() {
        return temperatureValue;
    }
}

