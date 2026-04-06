package kz.enu.hospitalmanagement.repository;

import kz.enu.hospitalmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Пайдаланушыны username бойынша іздеу
    Optional<User> findByUsername(String username);

    // Пайдаланушыны email бойынша іздеу
    Optional<User> findByEmail(String email);

    // Пайдаланушының бар-жоғын тексеру
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}