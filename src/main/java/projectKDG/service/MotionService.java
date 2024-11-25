package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import projectKDG.domain.Motion;
import projectKDG.repository.MotionRepository;

import java.util.List;

@Service
public class MotionService {

    private final MotionRepository motionRepository;

    @Autowired
    public MotionService(MotionRepository motionRepository) {
        this.motionRepository = motionRepository;
    }

    @GetMapping
    public List<Motion> getMotions() {
        return motionRepository.findAll();
    }

    public void addNewMotion(Motion motion) {
        motionRepository.findMotionByMotionSensorId(motion.getMotionSensorId());
        if (motion.isMotionSensorStatus() != motionRepository.findLastMotion().isMotionSensorStatus()){
            motionRepository.save(motion);
            System.out.println("Motion data saved: " + motion);
            System.out.println(motion);
        }
    }

    public List<Motion> getLatestMotions() {
        return motionRepository.findLatestMotions();
    }

}