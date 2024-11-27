package projectKDG.processor;

import org.springframework.stereotype.Component;
import projectKDG.eventhandler.SensorEvent;
import projectKDG.eventhandler.TemperatureEvent;

@Component
public class TemperatureSensorProcessor implements SensorProcessor {
    @Override
    public void process(SensorEvent event) {
        TemperatureEvent temperatureEvent = (TemperatureEvent) event;
        System.out.println("Processing temperature event: " + temperatureEvent.getTemperatureValue());
    }
}
