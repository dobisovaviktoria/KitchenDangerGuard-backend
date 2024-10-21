package domain;

public class Sensor {
    private int SensorId;
    private SensorType type;

    public Sensor(int sensorId, SensorType type) {
        SensorId = sensorId;
        this.type = type;
    }

    public int getSensorId() {
        return SensorId;
    }

    public SensorType getType() {
        return type;
    }
}