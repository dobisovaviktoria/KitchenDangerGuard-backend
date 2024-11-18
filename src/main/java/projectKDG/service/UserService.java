package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectKDG.domain.User;
import projectKDG.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save a new user to the database
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // Retrieve all users from the database (if needed)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
