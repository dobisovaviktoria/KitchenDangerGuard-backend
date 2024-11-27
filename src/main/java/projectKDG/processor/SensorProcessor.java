package projectKDG.processor;

import projectKDG.eventhandler.SensorEvent;

public interface SensorProcessor {
    void process(SensorEvent event);
}
