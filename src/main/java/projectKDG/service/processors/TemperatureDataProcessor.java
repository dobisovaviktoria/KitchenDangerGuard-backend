package projectKDG.service.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projectKDG.domain.SensorData;

public class TemperatureDataProcessor implements DataProcessor {
    private static final Logger log = LoggerFactory.getLogger(TemperatureDataProcessor.class);

    @Override
    public void process(SensorData sensorData) {
        if (sensorData.getTemperatureValue() != null) {
            log.info("Processing temperature value: {}", sensorData.getTemperatureValue());
            // Add additional processing logic here if necessary
        } else {
            log.warn("Temperature value is null for SensorData with ID {}", sensorData.getId());
        }
    }
}