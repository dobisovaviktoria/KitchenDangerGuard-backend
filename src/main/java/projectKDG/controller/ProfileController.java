package projectKDG.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import projectKDG.domain.User;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login"; // Redirect to login if not logged in
        }
        model.addAttribute("user", user);
        return "profile";
    }
}
