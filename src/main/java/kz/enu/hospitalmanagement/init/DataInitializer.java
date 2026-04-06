package kz.enu.hospitalmanagement.init;

import kz.enu.hospitalmanagement.entities.User;
import kz.enu.hospitalmanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Админ пайдаланушыны қосу
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@hospital.kz");
            admin.setFullName("Система Администраторы");
            admin.setRole("ADMIN");  // Рөлі ADMIN
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println("✅ Админ пайдаланушысы қосылды: admin / admin123");
        }

        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setEmail("user@hospital.kz");
            user.setFullName("Қарапайым Пайдаланушы");
            user.setRole("USER");  // Рөлі USER
            user.setEnabled(true);
            userRepository.save(user);
            System.out.println("✅ Қарапайым пайдаланушы қосылды: user / user123");
        }
    }
}