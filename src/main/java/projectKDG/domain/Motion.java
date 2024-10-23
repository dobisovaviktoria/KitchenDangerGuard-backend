    package projectKDG.domain;

    import jakarta.persistence.*;
    import java.time.LocalDateTime;

    import static jakarta.persistence.GenerationType.SEQUENCE;

    @Entity
    @Table(name = "motion_data")
    public class Motion {

        @Id
        @SequenceGenerator(
                name = "sequence_motion",
                sequenceName = "sequence_motion",
                allocationSize = 1
        )
        @GeneratedValue(
                strategy = SEQUENCE,
                generator = "sequence_motion"
        )
        @Column(
                name = "motion_sensor_id",
                updatable = false
        )
        private Integer motionSensorId;

        @Column(
                name = "motion_sensor_status")
        private boolean motionSensorStatus;

        @Column(
                name = "motion_timestamp"
        )
        private LocalDateTime motionTimestamp;

        public Motion() {
        }

        public Motion(boolean motionSensorStatus, LocalDateTime motionTimestamp) {
            this.motionSensorStatus = motionSensorStatus;
            this.motionTimestamp = motionTimestamp;
        }

        public Integer getMotionSensorId() {
            return motionSensorId;
        }

        public boolean isMotionSensorStatus() {
            return motionSensorStatus;
        }

        public void setMotionSensorStatus(boolean motionSensorStatus) {
            this.motionSensorStatus = motionSensorStatus;
        }

        public LocalDateTime getMotionTimestamp() {
            return motionTimestamp;
        }

        public void setMotionTimestamp(LocalDateTime motionTimestamp) {
            this.motionTimestamp = motionTimestamp;
        }

    }