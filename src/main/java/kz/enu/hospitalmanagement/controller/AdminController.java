package kz.enu.hospitalmanagement.controller;

import kz.enu.hospitalmanagement.entities.User;
import kz.enu.hospitalmanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("")
    public String adminIndex(Model model) {
        model.addAttribute("userCount", userRepository.count());
        return "admin/index";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/create-user";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        user.setEnabled(true);
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("message", "Пайдаланушы сәтті қосылды!");
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Пайдаланушы табылмады!");
            return "redirect:/admin/users";
        }
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User updatedUser,
                             RedirectAttributes redirectAttributes) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            redirectAttributes.addFlashAttribute("error", "Пайдаланушы табылмады!");
            return "redirect:/admin/users";
        }

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setRole(updatedUser.getRole());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userRepository.save(existingUser);

        redirectAttributes.addFlashAttribute("message", "Пайдаланушы сәтті жаңартылды!");
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && "ADMIN".equals(user.getRole())) {
            redirectAttributes.addFlashAttribute("error", "Администраторды жоюға болмайды!");
            return "redirect:/admin/users";
        }

        userRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Пайдаланушы сәтті жойылды!");
        return "redirect:/admin/users";
    }
}