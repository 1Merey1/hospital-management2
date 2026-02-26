package kz.enu.hospitalmanagement.entities;

public class Cabinet {
    private Long id;
    private int number;          // Номер кабинета (например, 101, 205)
    private String floor;         // Этаж
    private String description;   // Описание (например, "Кабинет кардиолога")
    private boolean isAvailable;  // Доступен ли кабинет

    public Cabinet() {
    }

    public Cabinet(Long id, int number, String floor, String description, boolean isAvailable) {
        this.id = id;
        this.number = number;
        this.floor = floor;
        this.description = description;
        this.isAvailable = isAvailable;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}