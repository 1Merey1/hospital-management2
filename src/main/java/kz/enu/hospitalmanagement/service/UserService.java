package kz.enu.hospitalmanagement.service;

import kz.enu.hospitalmanagement.entities.User;
import kz.enu.hospitalmanagement.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // ============ БИЗНЕС ЕРЕЖЕЛЕРІН ТЕКСЕРУ ============

    /**
     * Бизнес ереже №1: Пайдаланушы бірегей болуы керек
     */
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Бизнес ереже №2: Email бірегей болуы керек
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Бизнес ереже №3: Пароль кемінде 6 символ
     */
    public boolean isPasswordValid(String password) {
        return password != null && password.length() >= 6;
    }

    /**
     * Бизнес ереже №4: Email дұрыс форматта
     */
    public boolean isEmailValid(String email) {
        if (email == null) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Бизнес ереже №5: Логин тек әріптер, сандар және _
     */
    public boolean isUsernameValid(String username) {
        if (username == null) return false;
        return username.matches("^[a-zA-Z0-9_]+$");
    }

    // ============ НЕГІЗГІ ОПЕРАЦИЯЛАР ============

    /**
     * Жаңа пайдаланушыны тіркеу (толық бизнес логикамен)
     */
    @Transactional
    public User registerUser(User user) {
        // Бизнес ережелерін тексеру
        if (userExists(user.getUsername())) {
            throw new RuntimeException("Бұл логин бос емес! Басқа логин таңдаңыз.");
        }

        if (emailExists(user.getEmail())) {
            throw new RuntimeException("Бұл email бос емес! Басқа email мекенжайын қолданыңыз.");
        }

        if (!isPasswordValid(user.getPassword())) {
            throw new RuntimeException("Құпия сөз кемінде 6 символ болуы керек!");
        }

        if (!isEmailValid(user.getEmail())) {
            throw new RuntimeException("Email дұрыс форматта болуы керек!");
        }

        if (!isUsernameValid(user.getUsername())) {
            throw new RuntimeException("Логин тек әріптер, сандар және _ символынан тұруы керек!");
        }

        // Парольді шифрлау
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Рөлді орнату
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        user.setEnabled(true);

        // Сақтау
        User savedUser = userRepository.save(user);

        // Email жіберу (бизнес ереже)
        try {
            emailService.sendWelcomeEmail(user.getEmail(), user.getUsername());
            System.out.println("✅ Құттықтау хаты жіберілді: " + user.getEmail());
        } catch (Exception e) {
            System.err.println("❌ Құттықтау хатын жіберу қатесі: " + e.getMessage());
            // Email жіберілмесе де, тіркелу жалғасады
        }

        return savedUser;
    }

    /**
     * Барлық пайдаланушыларды алу
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Пайдаланушыны ID бойынша іздеу
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Пайдаланушыны username бойынша іздеу
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Пайдаланушылар санын алу
     */
    public long getUserCount() {
        return userRepository.count();
    }
}