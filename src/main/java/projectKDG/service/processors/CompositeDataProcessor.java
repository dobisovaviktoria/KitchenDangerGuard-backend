package projectKDG.service.processors;

import projectKDG.domain.SensorData;

import java.util.ArrayList;
import java.util.List;

public class CompositeDataProcessor implements DataProcessor {
    private final List<DataProcessor> processors = new ArrayList<>();

    public void addProcessor(DataProcessor processor) {
        processors.add(processor);
    }

    @Override
    public void process(SensorData sensorData) {
        for (DataProcessor processor : processors) {
            processor.process(sensorData);
        }
    }
}
