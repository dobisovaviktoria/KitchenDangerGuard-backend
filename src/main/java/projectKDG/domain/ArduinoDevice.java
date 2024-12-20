package projectKDG.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "arduino_device")
public class ArduinoDevice {

    @Id
    private int arduinoDeviceId;
    /// /Relationships
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    //Constructors
    public ArduinoDevice(int arduinoDeviceId) {
        this.arduinoDeviceId = arduinoDeviceId;
    }

    public ArduinoDevice() {
    }

    //getter & setter
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
}