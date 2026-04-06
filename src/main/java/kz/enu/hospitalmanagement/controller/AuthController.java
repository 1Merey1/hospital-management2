package kz.enu.hospitalmanagement.controller;

import jakarta.validation.Valid;
import kz.enu.hospitalmanagement.entities.User;
import kz.enu.hospitalmanagement.repository.UserRepository;
import kz.enu.hospitalmanagement.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;  // EmailService қосу

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          EmailService emailService) {  // Конструкторға қосу
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Қате логин немесе пароль!");
        }

        if (logout != null) {
            model.addAttribute("message", "Сіз жүйеден сәтті шықтыңыз!");
        }

        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        // Логин бос емес екенін тексеру
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            result.rejectValue("username", "error.user", "Логин бос болмауы керек");
        }

        // Логин бірегейлігін тексеру
        if (userRepository.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Бұл логин бос емес");
        }

        // Email бірегейлігін тексеру
        if (userRepository.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Бұл email бос емес");
        }

        if (result.hasErrors()) {
            return "register";
        }

        // Парольді шифрлау
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setEnabled(true);

        userRepository.save(user);

        // ⭐⭐⭐ EMAIL ЖІБЕРУ - ТІРКЕЛУ КЕЗІНДЕ ⭐⭐⭐
        try {
            emailService.sendWelcomeEmail(user.getEmail(), user.getUsername());
            System.out.println("✅ Құттықтау хаты жіберілді: " + user.getEmail());
        } catch (Exception e) {
            System.err.println("❌ Құттықтау хатын жіберу қатесі: " + e.getMessage());
            // Email жіберілмесе де, тіркелу жалғасады
        }

        redirectAttributes.addFlashAttribute("message", "Тіркелу сәтті өтті! Жүйеге кіріңіз. Email мекенжайыңызға құттықтау хаты жіберілді.");
        return "redirect:/login?registered=true";
    }
}