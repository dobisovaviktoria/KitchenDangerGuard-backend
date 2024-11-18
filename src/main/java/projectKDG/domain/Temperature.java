package projectKDG.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "temperature_data")
public class Temperature {

    @SequenceGenerator(
            name = "sequence_temp",
            sequenceName= "sequence_temp",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_temp"
    )
    @Column(
            name = "temp_id",
            updatable = false
    )
    @Id
    private Integer temperatureSensorId;

    @Column(
            name = "temp_value"
    )
    private float temperatureSensorValue;

    @Column(
            name = "temp_timestamp"
    )
    private LocalDateTime temperatureTimestamp;

    public Temperature() {}
    public Temperature( float temperatureSensorValue, LocalDateTime temperatureTimestamp) {
        this.temperatureSensorValue = temperatureSensorValue;
        this.temperatureTimestamp = temperatureTimestamp;
    }

    public Integer getTemperatureSensorId() {
        return temperatureSensorId;
    }

    public void setTemperatureSensorId(Integer temperatureSensorId) {
        this.temperatureSensorId = temperatureSensorId;
    }

    public float getTemperatureSensorValue() {
        return temperatureSensorValue;
    }

    public void setTemperatureSensorValue(float temperatureSensorValue) {
        this.temperatureSensorValue = temperatureSensorValue;
    }

    public LocalDateTime getTemperatureTimestamp() {
        return temperatureTimestamp;
    }

    public void setTemperatureTimestamp(LocalDateTime temperatureTimestamp) {
        this.temperatureTimestamp = temperatureTimestamp;
    }
}
