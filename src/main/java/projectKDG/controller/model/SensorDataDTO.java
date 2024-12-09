package projectKDG.controller.model;

public class SensorDataDTO {

    private boolean motionStatus;
    private float temperatureValue;
    private int deviceId;

    // Constructors
    public SensorDataDTO() {}

    public SensorDataDTO(boolean motionStatus, float temperatureValue, int deviceId) {
        this.motionStatus = motionStatus;
        this.temperatureValue = temperatureValue;
        this.deviceId = deviceId;
    }

    // Getters and Setters
    public boolean getMotionStatus() {
        return motionStatus;
    }

    public void setMotionStatus(boolean motionStatus) {
        this.motionStatus = motionStatus;
    }

    public float getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(float temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}
