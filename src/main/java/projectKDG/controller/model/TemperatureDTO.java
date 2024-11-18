package projectKDG.controller.model;

public class TemperatureDTO {
    float temperatureValue;

    public TemperatureDTO(float value) {
        temperatureValue = value;
    }

    public float getTemperatureValue() {
        return temperatureValue;
    };

    public void setTemperatureValue(float temperatureValue) {
        this.temperatureValue = temperatureValue;
    };
}
