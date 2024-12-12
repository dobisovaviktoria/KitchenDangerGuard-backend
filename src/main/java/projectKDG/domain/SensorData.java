package projectKDG.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_data")
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_data_seq")
    @SequenceGenerator(name = "sensor_data_seq", sequenceName = "sensor_data_seq", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "motion_status")
    private Boolean motionStatus;

    @Column(name = "temperature_value")
    private Float temperatureValue;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "arduino_device_id", nullable = false)
    private ArduinoDevice arduinoDevice;


    // Constructors
    public SensorData() {
    }

    public SensorData(Boolean motionStatus, Float temperatureValue, LocalDateTime timestamp) {
        this.motionStatus = motionStatus;
        this.temperatureValue = temperatureValue;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ArduinoDevice getArduinoDevice() {
        return arduinoDevice;
    }

    public void setArduinoDevice(ArduinoDevice arduinoDevice) {
        this.arduinoDevice = arduinoDevice;
    }
}