package kz.enu.hospitalmanagement.controller;

import kz.enu.hospitalmanagement.entities.User;
import kz.enu.hospitalmanagement.repository.UserRepository;
import kz.enu.hospitalmanagement.service.EmailService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public ProfileController(UserRepository userRepository,
                             PasswordEncoder passwordEncoder,
                             EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Пайдаланушы табылмады!");
            return "redirect:/profile";
        }

        // Ағымдағы парольді тексеру
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Ағымдағы құпия сөз қате!");
            return "redirect:/profile";
        }

        // Жаңа парольді тексеру
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Жаңа құпия сөздер сәйкес келмейді!");
            return "redirect:/profile";
        }

        if (newPassword.length() < 4) {
            redirectAttributes.addFlashAttribute("error", "Құпия сөз кемінде 4 таңбадан тұруы керек!");
            return "redirect:/profile";
        }

        // Парольді жаңарту
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // ⭐⭐⭐ EMAIL ЖІБЕРУ - ПАРОЛЬ ӨЗГЕРТІЛГЕНДЕ ⭐⭐⭐
        try {
            emailService.sendPasswordChangeEmail(user.getEmail(), user.getUsername());
            System.out.println("✅ Пароль өзгерту хаты жіберілді: " + user.getEmail());
        } catch (Exception e) {
            System.err.println("❌ Пароль өзгерту хатын жіберу қатесі: " + e.getMessage());
        }

        redirectAttributes.addFlashAttribute("success", "Құпия сөз сәтті өзгертілді! Email мекенжайыңызға хабарлама жіберілді.");
        return "redirect:/profile";
    }
}