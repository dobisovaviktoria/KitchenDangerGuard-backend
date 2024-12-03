package projectKDG.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "arduino_device")
public class ArduinoDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int arduinoDeviceId;
////Relationships
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "arduinoDevice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SensorData> sensorDataList = new ArrayList<>();


    //Constructors
    public ArduinoDevice(int arduinoDeviceId) {
        this.arduinoDeviceId = arduinoDeviceId;
    }

    public ArduinoDevice() {
    }
    //gettter & setter
    public int getArduinoDeviceId() {
        return arduinoDeviceId;
    }

    public void setArduinoDeviceId(int arduinoDeviceId) {
        this.arduinoDeviceId = arduinoDeviceId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //Methods
    public List<SensorData> getSensorDataList() {
        return sensorDataList;
    }

    public void setSensorDataList(List<SensorData> sensorDataList) {
        this.sensorDataList = sensorDataList;
    }

    public void addSensorData(SensorData sensorData) {
        sensorData.setArduinoDevice(this);
        this.sensorDataList.add(sensorData);
    }

    public void removeSensorData(SensorData sensorData) {
        sensorData.setArduinoDevice(null);
        this.sensorDataList.remove(sensorData);
    }
}
