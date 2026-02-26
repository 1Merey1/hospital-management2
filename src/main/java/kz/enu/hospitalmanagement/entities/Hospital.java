package kz.enu.hospitalmanagement.entities;

public class Hospital {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    // Для одной больницы сделаем статическое поле
    private static Hospital instance = null;

    // Приватный конструктор (паттерн Singleton)
    private Hospital() {
        this.id = 1L;
        this.name = "Городская центральная больница";
        this.address = "г. Астана, ул. Абая, 10";
        this.phoneNumber = "+7 (7172) 123-456";
        this.email = "info@centralhospital.kz";
    }

    // Метод для получения единственного экземпляра больницы
    public static Hospital getInstance() {
        if (instance == null) {
            instance = new Hospital();
        }
        return instance;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}