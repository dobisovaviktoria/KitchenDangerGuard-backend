package projectKDG.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projectKDG.domain.NotificationPreference;
import projectKDG.domain.User;
import projectKDG.service.UserService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

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
        model.addAttribute("preferences", List.of(NotificationPreference.values()));
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

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    // POST request to handle login form submission
    @PostMapping("/login")
    public String processLogin(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {
        if (userService.validateUser(email, password)) {
            // Store user in the session
            session.setAttribute("loggedInUser", userService.findByEmail(email).orElse(null));
            return "redirect:/profile";  // Redirect to a profile page on success
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";  // Return to the login page with an error message
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Invalidate the session
        return "redirect:/login";  // Redirect to login page
    }

    @GetMapping("/home")
    public String homePage(HttpSession session) {
        return "home";
    }

    @GetMapping("/team")
    public String teamPage(HttpSession session) {
        return "team";
    }

    @GetMapping("/product")
    public String productPage(HttpSession session) {
        return "product";
    }

    @GetMapping("/contact")
    public String redirectToEmail() {
        return "redirect:mailto:kitchendangerguard@gmail.com";
    }

}