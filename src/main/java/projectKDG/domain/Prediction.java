package projectKDG.domain;

public  class Prediction {
    private double temperature;
    private String timeframe;

    // Default constructor
    public Prediction() {}

    // Constructor for easier deserialization
    public Prediction(double temperature, String timeframe) {
        this.temperature = temperature;
        this.timeframe = timeframe;
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
}