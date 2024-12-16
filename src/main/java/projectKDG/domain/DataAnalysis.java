package projectKDG.domain;

public class DataAnalysis {
    private String timestamp;
    private double temperatureValue;
    private int motionStatus;

    public int getMotionStatus() {
        return motionStatus;
    }

    public void setMotionStatus(int motionStatus) {
        this.motionStatus = motionStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(double temperatureValue) {
        this.temperatureValue = temperatureValue;
    }
}
