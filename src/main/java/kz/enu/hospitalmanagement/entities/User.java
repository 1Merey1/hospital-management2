package kz.enu.hospitalmanagement.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // БИЗНЕС ЕРЕЖЕ: Логин 3-50 символ, тек әріптер, сандар және _
    @NotBlank(message = "Логин бос болмауы керек")
    @Size(min = 3, max = 50, message = "Логин 3-50 символ аралығында болуы керек")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Логин тек әріптер, сандар және _ символынан тұруы керек")
    @Column(unique = true, nullable = false)
    private String username;

    // БИЗНЕС ЕРЕЖЕ: Пароль кемінде 6 символ
    @NotBlank(message = "Құпия сөз бос болмауы керек")
    @Size(min = 6, message = "Құпия сөз кемінде 6 символ болуы керек")
    @Column(nullable = false)
    private String password;

    // БИЗНЕС ЕРЕЖЕ: Email дұрыс форматта
    @NotBlank(message = "Email бос болмауы керек")
    @Email(message = "Email дұрыс форматта болуы керек (мысалы: user@mail.com)")
    @Column(unique = true, nullable = false)
    private String email;

    // БИЗНЕС ЕРЕЖЕ: Аты-жөні 2-100 символ
    @NotBlank(message = "Аты-жөні бос болмауы керек")
    @Size(min = 2, max = 100, message = "Аты-жөні 2-100 символ аралығында болуы керек")
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String role = "USER"; // DEFAULT ROLE IS USER

    private boolean enabled = true;

    // ============ КОНСТРУКТОРЛАР ============

    // 1. Пустой конструктор (обязателен для JPA)
    public User() {
    }

    // 2. Полный конструктор
    public User(Long id, String username, String password, String email, String fullName, String role, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.enabled = enabled;
    }

    // 3. Конструктор без ID (для создания новых пользователей)
    public User(String username, String password, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.role = "USER";
        this.enabled = true;
    }

    // ============ ГЕТТЕРЫ И СЕТТЕРЫ ============

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // ============ БИЗНЕС ЕРЕЖЕЛЕРІН ТЕКСЕРУ МЕТОДТАРЫ ============

    /**
     * Бизнес ереже: Пайдаланушының админ екенін тексеру
     */
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }

    /**
     * Бизнес ереже: Пайдаланушының активті екенін тексеру
     */
    public boolean isActive() {
        return this.enabled;
    }

    /**
     * Бизнес ереже: Пайдаланушының рөлін өзгертуге рұқсат бар ма
     */
    public boolean canChangeRole(User currentUser) {
        // Тек админ ғана рөлді өзгерте алады
        return currentUser != null && currentUser.isAdmin();
    }

    /**
     * Бизнес ереже: Пайдаланушы өзін-өзі жоя ала ма
     */
    public boolean canDeleteSelf(User currentUser) {
        // Админ өзін-өзі жоя алмайды
        if (this.isAdmin() && this.equals(currentUser)) {
            return false;
        }
        return true;
    }

    // ============ EQUALS, HASHCODE, TOSTRING ============

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled &&
                Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(fullName, user.fullName) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, fullName, role, enabled);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}