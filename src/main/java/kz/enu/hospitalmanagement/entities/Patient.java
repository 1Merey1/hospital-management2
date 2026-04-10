package kz.enu.hospitalmanagement.entities;  // ⚠️ ПАКЕТТІ entity деп ӨЗГЕРТІҢІЗ

import jakarta.persistence.*;  // ⚠️ БҰЛ ИМПОРТТЫ ҚОСЫҢЫЗ

@Entity  // ⚠️ БҰЛ АННОТАЦИЯНЫ ҚОСЫҢЫЗ
@Table(name = "patients")  // ⚠️ БҰЛ АННОТАЦИЯНЫ ҚОСЫҢЫЗ
public class Patient {

    @Id  // ⚠️ БҰЛ АННОТАЦИЯНЫ ҚОСЫҢЫЗ
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ⚠️ БҰЛ АННОТАЦИЯНЫ ҚОСЫҢЫЗ
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private int age;
    private String diagnosis;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "doctor_id")
    private Long doctorId;

    public Patient() {
    }

    public Patient(Long id, String firstName, String lastName, int age,
                   String diagnosis, String phoneNumber, Long doctorId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.diagnosis = diagnosis;
        this.phoneNumber = phoneNumber;
        this.doctorId = doctorId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}