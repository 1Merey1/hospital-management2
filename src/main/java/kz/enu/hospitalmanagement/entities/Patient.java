package kz.enu.hospitalmanagement.entities;

public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String diagnosis;
    private String phoneNumber;

    // Какой доктор лечит пациента
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