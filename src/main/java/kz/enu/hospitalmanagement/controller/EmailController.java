package kz.enu.hospitalmanagement.controller;

import kz.enu.hospitalmanagement.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // Email тестілеу беті
    @GetMapping("/test")
    public String testEmailPage() {
        return "email/test";
    }

    // Тесттік email жіберу
    @PostMapping("/send-test")
    public String sendTestEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String message,
            RedirectAttributes redirectAttributes) {

        try {
            emailService.sendSimpleEmail(to, subject, message);
            redirectAttributes.addFlashAttribute("success", "Email сәтті жіберілді!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Email жіберу қатесі: " + e.getMessage());
        }

        return "redirect:/email/test";
    }
}