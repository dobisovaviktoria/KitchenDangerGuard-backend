package domain;

import java.time.LocalDate;

public class Temperature {
    private float value;
    private LocalDate timestamp;

    public float getValue() {
        return value;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }
}
