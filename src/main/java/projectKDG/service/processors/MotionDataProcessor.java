package projectKDG.service.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projectKDG.domain.SensorData;

public class MotionDataProcessor implements DataProcessor {
    private static final Logger log = LoggerFactory.getLogger(MotionDataProcessor.class);

    @Override
    public void process(SensorData sensorData) {
        if (sensorData.getMotionStatus() != null) {
            log.info("Processing motion status: {}", sensorData.getMotionStatus() ? "Motion detected" : "No motion detected");
            // Add additional processing logic here if necessary
        } else {
            log.warn("Motion status is null for SensorData with ID {}", sensorData.getId());
        }
    }
}