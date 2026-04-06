package kz.enu.hospitalmanagement.controller;

import kz.enu.hospitalmanagement.entities.User;
import kz.enu.hospitalmanagement.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")  // Барлық методтар /profile/... арқылы жұмыс істейді
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Енді бұл /profile/info арқылы қолжетімді
    @GetMapping("/info")
    public String showProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        model.addAttribute("user", user);
        return "profile";
    }

    // Пароль өзгерту формасы
    @GetMapping("/change-password")
    public String changePasswordForm() {
        return "profile/change-password";
    }
}