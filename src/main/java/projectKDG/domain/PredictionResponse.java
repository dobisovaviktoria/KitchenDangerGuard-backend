package projectKDG.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PredictionResponse {

    @JsonProperty("arduino_id")
    private int arduinoId;

    @JsonProperty("future_prediction")
    private Prediction futurePrediction;

    public PredictionResponse(int arduinoId, Prediction futurePrediction) {
        this.arduinoId = arduinoId;
        this.futurePrediction = futurePrediction;
    }

    public PredictionResponse(String noPrediction) {
    }

    // Getters and Setters
    public int getArduinoId() {
        return arduinoId;
    }

    public void setArduinoId(int arduinoId) {
        this.arduinoId = arduinoId;
    }

    public Prediction getFuturePrediction() {
        return futurePrediction;
    }

    public void setFuturePrediction(Prediction futurePrediction) {
        this.futurePrediction = futurePrediction;
    }

    public static class Prediction {

        private double temperature;
        private String timeframe;
        private String message; // For string-based prediction messages

        // Default constructor
        public Prediction() {
        }

        // Constructor for object-based predictions (temperature and timeframe)
        @JsonCreator
        public Prediction(@JsonProperty("temperature") double temperature, @JsonProperty("timeframe") String timeframe) {
            this.temperature = temperature;
            this.timeframe = timeframe;
        }

        // Constructor for string-based predictions (e.g., "No dangerous prediction in the next 2 hours")
        public Prediction(String message) {
            this.message = message;
        }

        // Getters and Setters
        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }

        public String getTimeframe() {
            return timeframe;
        }

        public void setTimeframe(String timeframe) {
            this.timeframe = timeframe;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
