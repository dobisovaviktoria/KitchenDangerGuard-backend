package projectKDG.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import projectKDG.domain.ArduinoDevice;
import projectKDG.domain.PredictionResponse;
import projectKDG.domain.User;
import projectKDG.service.PredictionService;

@Controller
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping("/prediction")
    public String showPredictionPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        ArduinoDevice arduinoDevice = user.getArduinoDevice();
        int device_id = arduinoDevice.getArduinoDeviceId();

        PredictionResponse response = predictionService.getPredictions(device_id);
        model.addAttribute("response", response);  // Add response to model for Thymeleaf

        return "prediction";
    }

    @GetMapping("/prediction-data")
    @ResponseBody  // This tells Spring to return the data as JSON
    public PredictionResponse getPredictionData(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        ArduinoDevice arduinoDevice = user.getArduinoDevice();
        int device_id = arduinoDevice.getArduinoDeviceId();

        return predictionService.getPredictions(device_id);  // Return prediction as JSON
    }
}