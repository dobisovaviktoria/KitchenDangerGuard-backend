package projectKDG.controller.model;

@Deprecated
public class MotionDTO {
    private boolean motionStatus;

    public MotionDTO() {
    }

    public MotionDTO(boolean motionStatus) {
        this.motionStatus = motionStatus;
    }

    public boolean isMotionStatus() {
        return motionStatus;
    }

    public void setMotionStatus(boolean motionStatus) {
        this.motionStatus = motionStatus;
    }
}