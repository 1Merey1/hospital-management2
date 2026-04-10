package kz.enu.hospitalmanagement.entities;  // ⚠️ ПАКЕТТІ entity деп ӨЗГЕРТІҢІЗ

import jakarta.persistence.*;  // ⚠️ БҰЛ ИМПОРТТЫ ҚОСЫҢЫЗ

@Entity  // ⚠️ БҰЛ АННОТАЦИЯНЫ ҚОСЫҢЫЗ
@Table(name = "doctors")  // ⚠️ БҰЛ АННОТАЦИЯНЫ ҚОСЫҢЫЗ
public class Doctor {

    @Id  // ⚠️ БҰЛ АННОТАЦИЯНЫ ҚОСЫҢЫЗ
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ⚠️ БҰЛ АННОТАЦИЯНЫ ҚОСЫҢЫЗ
    private Long id;

    @Column(name = "first_name")  // ⚠️ БҰЛ АННОТАЦИЯНЫ ҚОСЫҢЫЗ
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String specialization;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @Column(name = "cabinet_id")
    private Long cabinetId;

    public Doctor() {
    }

    public Doctor(Long id, String firstName, String lastName, String specialization,
                  String phoneNumber, String email, Long cabinetId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.cabinetId = cabinetId;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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

    public Long getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(Long cabinetId) {
        this.cabinetId = cabinetId;
    }
}