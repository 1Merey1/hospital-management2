package kz.enu.hospitalmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Қарапайым мәтіндік email жіберу
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            System.out.println("✅ Email сәтті жіберілді: " + to);
        } catch (Exception e) {
            System.err.println("❌ Email жіберу қатесі: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * HTML форматында email жіберу
     */
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML формат

            mailSender.send(message);
            System.out.println("✅ HTML Email сәтті жіберілді: " + to);
        } catch (MessagingException e) {
            System.err.println("❌ HTML Email жіберу қатесі: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Thymeleaf шаблонымен email жіберу
     */
    public void sendTemplateEmail(String to, String subject, String templateName, Context context) {
        try {
            String htmlContent = templateEngine.process(templateName, context);
            sendHtmlEmail(to, subject, htmlContent);
        } catch (Exception e) {
            System.err.println("❌ Template email жіберу қатесі: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ============ АРНАЙЫ ХАБАРЛАМАЛАР ============

    /**
     * Тіркелу сәтті өткенде құттықтау хаты
     */
    public void sendWelcomeEmail(String to, String username) {
        String subject = "🏥 Hospital Management жүйесіне қош келдіңіз!";
        String text = String.format(
                "Құрметті %s,\n\n" +
                        "Hospital Management жүйесіне сәтті тіркелдіңіз!\n\n" +
                        "Логиніңіз: %s\n\n" +
                        "Жүйеге кіру үшін: http://localhost:8080/login\n\n" +
                        "Қош келдіңіз!\n\n" +
                        "Құрметпен, Hospital Management командасы",
                username, username
        );
        sendSimpleEmail(to, subject, text);
    }

    /**
     * Пароль өзгертілгенде хабарлама
     */
    public void sendPasswordChangeEmail(String to, String username) {
        String subject = "🔐 Пароль өзгертілді - Hospital Management";
        String text = String.format(
                "Құрметті %s,\n\n" +
                        "Сіздің құпия сөзіңіз сәтті өзгертілді.\n\n" +
                        "Егер бұл әрекетті сіз жасамаған болсаңыз, дереу әкімшіге хабарласыңыз.\n\n" +
                        "Құрметпен, Hospital Management командасы",
                username
        );
        sendSimpleEmail(to, subject, text);
    }

    /**
     * Жаңа дәрігер тағайындалғанда хабарлама
     */
    public void sendDoctorAssignedEmail(String to, String patientName, String doctorName) {
        String subject = "👨‍⚕️ Дәрігер тағайындалды - Hospital Management";
        String text = String.format(
                "Құрметті %s,\n\n" +
                        "Сізге дәрігер тағайындалды: %s\n\n" +
                        "Дәрігермен байланысу үшін жүйеге кіріңіз.\n\n" +
                        "Құрметпен, Hospital Management командасы",
                patientName, doctorName
        );
        sendSimpleEmail(to, subject, text);
    }

    /**
     * Емдеу кестесі туралы хабарлама
     */
    public void sendAppointmentEmail(String to, String patientName, String doctorName, String dateTime) {
        String subject = "📅 Қабылдау кестесі - Hospital Management";
        String text = String.format(
                "Құрметті %s,\n\n" +
                        "Сіздің дәрігер %s қабылдауыңыз %s күніне тағайындалды.\n\n" +
                        "Уақытында келуіңізді сұраймыз.\n\n" +
                        "Құрметпен, Hospital Management командасы",
                patientName, doctorName, dateTime
        );
        sendSimpleEmail(to, subject, text);
    }
}