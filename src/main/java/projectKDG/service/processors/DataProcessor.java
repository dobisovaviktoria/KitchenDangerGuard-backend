package projectKDG.service.processors;

import projectKDG.domain.SensorData;

//component
public interface DataProcessor {
    void process(SensorData sensorData);
}
