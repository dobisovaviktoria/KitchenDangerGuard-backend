package projectKDG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projectKDG.domain.Motion;
import projectKDG.service.MotionService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(path = "motion")
public class MotionController {

    private final MotionService motionService;

     @Autowired
    public MotionController(MotionService motionService) {
         this.motionService = motionService;
     }

    @GetMapping
    public List<Motion> getMotions() {
        return motionService.getMotions() ;
    }

    @PostMapping
    public void addMotion(@RequestBody Motion motion) {
         motionService.addNewMotion(motion);
    }

}
