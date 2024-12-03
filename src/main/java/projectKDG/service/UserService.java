package projectKDG.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectKDG.domain.User;
import projectKDG.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // Save a new user to the database
    public void saveUser(User user) {
        userRepository.save(user);
    }

    // Retrieve all users from the database (if needed)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    // Find user by email and validate password
    public boolean validateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent() && userOptional.get().getPassword().equals(password);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
