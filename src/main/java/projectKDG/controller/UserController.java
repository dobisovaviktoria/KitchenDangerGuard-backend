package projectKDG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projectKDG.domain.User;
import projectKDG.service.UserService;

@Controller
@RequestMapping("/signup")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET request to show the sign-up form
    @GetMapping
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());  // Create an empty User object to bind to the form
        return "signup";
    }

    // POST request to handle form submission and save user data
    @PostMapping
    public String processSignUp(@ModelAttribute("user") User user) {
        // Save the user object to the database
        userService.saveUser(user);
        return "redirect:/success";  // Redirect to a success page after saving
    }
}
