package projectKDG.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "user_data")
public class User {
    @Id

    @GeneratedValue(
            strategy = SEQUENCE
    )
    @Column(
            name = "user_id"
    )
    private int userID;

    @Column(
            name = "user_name"
    )
    private String userName;

    @Column(
            name = "age"
    )
    private LocalDate age;

    @Column(
            name = "password"
    )
    private String password;
    @Column(
            name = "email"
    )
    private String email;

    @Column(
            name = "phone"
    )
    private String phone;


    ////Relationships between arduinodevice and the user
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArduinoDevice arduinoDevice;

    public User(String phone, String email, String password, LocalDate age, String userName) {
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.age = age;
        this.userName = userName;
    }

    public User() {
    }

    public LocalDate getAge() {
        return age;
    }

    public void setAge(LocalDate age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArduinoDevice getArduinoDevice() {
        return arduinoDevice;
    }

    public void setArduinoDevice(ArduinoDevice arduinoDevice) {
        this.arduinoDevice = arduinoDevice;
    }
}