package projectKDG.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import projectKDG.domain.DataAnalysis;

import java.util.Arrays;
import java.util.List;

@Controller
public class DataAnalysisController {


    @GetMapping("/anomalies")
    public String fetchAnomalies(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:5000/anomalies";  // Python Flask API URL

        // Fetch JSON data from Python API
        ResponseEntity<DataAnalysis[]> response = restTemplate.getForEntity(url, DataAnalysis[].class);
        List<DataAnalysis> anomalies = Arrays.asList(response.getBody());

        // Add the anomalies to the model to pass to the Thymeleaf view
        model.addAttribute("anomalies", anomalies);
        return "anomalies";  // Return the Thymeleaf view name
    }
}
