package projectKDG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projectKDG.controller.model.MotionDTO;
import projectKDG.domain.Motion;
import projectKDG.service.MotionService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping()
public class MotionController {

    private final MotionService motionService;

    @Autowired
    public MotionController(MotionService motionService) {
        this.motionService = motionService;
    }

    // GET endpoint to retrieve all motion records
    @GetMapping("/motion")
    @ResponseBody
    public List<Motion> getMotions() {
        return motionService.getMotions();
    }

    // POST endpoint to receive motion data from Arduino
    @PostMapping("/motion")
    @ResponseBody
    public ResponseEntity<String> receiveMotionData(@RequestBody MotionDTO motionDto) {
        // Print the received boolean motion status
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Received motion data: " + motionDto.isMotionStatus() + " at " + now);

        // Save motion data to the database
        Motion motion = new Motion();
        motion.setMotionSensorStatus(motionDto.isMotionStatus());
        motion.setMotionTimestamp(now);
        motionService.addNewMotion(motion);

        // Return a response back to Arduino
        return new ResponseEntity<>(
                "Data received: " + (motion.isMotionSensorStatus() ? "Motion detected" : "No motion"),
                HttpStatus.CREATED
        );
    }

    // Provide the Thymeleaf page with motion data
    @GetMapping("/motion-data")
    public String getMotionData(Model model) {
        List<Motion> motions = motionService.getMotions();
        model.addAttribute("motions", motions);
        return "motion-data";
    }
}
