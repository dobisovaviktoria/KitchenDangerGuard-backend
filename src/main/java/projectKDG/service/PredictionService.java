package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import projectKDG.domain.PredictionResponse;

@Service
public class PredictionService {

    @Autowired
    private RestTemplate restTemplate;

    // Flask API URL
    private static final String FLASK_API_URL = "http://127.0.0.1:5000/predict/{deviceId}";

    public PredictionResponse getPredictions(int deviceId) {
        // Make a GET request to the Flask API with the deviceId
        return restTemplate.getForObject(FLASK_API_URL, PredictionResponse.class, deviceId);
    }
}
