package projectKDG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projectKDG.domain.User;
import projectKDG.service.UserService;

@Controller
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET request to show the sign-up form
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());  // Create an empty User object to bind to the form
        return "signup";
    }

    // POST request to handle form submission and save user data
    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("user") User user, Model model) {
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already exists");
            return "signup";
        }
        userService.saveUser(user);
        return "redirect:/login";  // Redirect to a success page after saving
    }

    // GET request to show the login form
    @GetMapping
    public String showLoginForm() {
        return "login";
    }

    // POST request to handle login form submission
    @PostMapping("/login")
    public String processLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {
        if (userService.validateUser(email, password)) {
            return "redirect:/home";  // Redirect to a home page on success
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";  // Return to the login page with an error message
        }
    }
}