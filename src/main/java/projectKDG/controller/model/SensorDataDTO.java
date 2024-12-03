package projectKDG.controller.model;

public class SensorDataDTO {

    private boolean motionStatus;
    private float temperatureValue;

    // Constructors
    public SensorDataDTO() {}

    public SensorDataDTO(boolean motionStatus, float temperatureValue) {
        this.motionStatus = motionStatus;
        this.temperatureValue = temperatureValue;
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
}
