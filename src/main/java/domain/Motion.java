package domain;

import java.time.LocalDate;

public class Motion {
    private boolean value;
    private LocalDate timestamp;

    public boolean isValue() {
        return value;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }
}
