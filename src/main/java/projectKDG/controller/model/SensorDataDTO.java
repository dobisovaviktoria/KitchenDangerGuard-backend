package projectKDG.controller.model;

public class SensorDataDTO {
    private Boolean motionStatus; // Nullable to handle cases where only temperature is sent
    private Float temperatureValue; // Nullable to handle cases where only motion is sent

    // Constructors
    public SensorDataDTO() {}

    public SensorDataDTO(Boolean motionStatus, Float temperatureValue) {
        this.motionStatus = motionStatus;
        this.temperatureValue = temperatureValue;
    }

    // Getters and setters
    public Boolean getMotionStatus() {
        return motionStatus;
    }

    public void setMotionStatus(Boolean motionStatus) {
        this.motionStatus = motionStatus;
    }

    public Float getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(Float temperatureValue) {
        this.temperatureValue = temperatureValue;
    }
}
