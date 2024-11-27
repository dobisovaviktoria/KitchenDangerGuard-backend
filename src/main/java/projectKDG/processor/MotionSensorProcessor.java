package projectKDG.processor;

import org.springframework.stereotype.Component;
import projectKDG.eventhandler.MotionEvent;
import projectKDG.eventhandler.SensorEvent;

@Component
public class MotionSensorProcessor implements SensorProcessor {
    @Override
    public void process(SensorEvent event) {
        MotionEvent motionEvent = (MotionEvent) event;
        System.out.println("Processing motion event: " + motionEvent.getMotionSensorId());
    }
}
