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
    private Integer temp_Id;

    @Column(
            name = "temp_value"
    )
    private float temp_value;

    @Column(
            name = "temp_timestamp"
    )
    private LocalDateTime temp_timestamp;

    public Temperature() {}
    public Temperature( float temp_value, LocalDateTime temp_timestamp) {
        this.temp_value = temp_value;
        this.temp_timestamp = temp_timestamp;
    }

    public float getTemp_value() {
        return temp_value;
    }

    public void setTemp_value(float temp_value) {
        this.temp_value = temp_value;
    }

    public LocalDateTime getTemp_timestamp() {
        return temp_timestamp;
    }

    public void setTemp_timestamp(LocalDateTime temp_timestamp) {
        this.temp_timestamp = temp_timestamp;
    }
}
