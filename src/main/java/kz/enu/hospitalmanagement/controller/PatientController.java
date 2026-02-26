package kz.enu.hospitalmanagement.controller;

import kz.enu.hospitalmanagement.entities.Patient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    // Временное хранилище данных (имитация базы данных)
    private List<Patient> patients = new ArrayList<>();
    private Long nextId = 1L;

    // Конструктор - добавим несколько тестовых пациентов
    public PatientController() {
        patients.add(new Patient(nextId++, "Иван", "Петров", 35, "Грипп", "+7-777-111-1111"));
        patients.add(new Patient(nextId++, "Мария", "Сидорова", 28, "Ангина", "+7-777-222-2222"));
        patients.add(new Patient(nextId++, "Алексей", "Иванов", 42, "Перелом", "+7-777-333-3333"));
    }

    // GET /api/patients - получить всех пациентов
    @GetMapping
    public List<Patient> getAllPatients() {
        return patients;
    }

    // GET /api/patients/{id} - получить пациента по ID
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        // Ищем пациента по ID
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null; // Если не нашли
    }

    // POST /api/patients - создать нового пациента
    @PostMapping
    public Patient createPatient(@RequestBody Patient newPatient) {
        newPatient.setId(nextId++);
        patients.add(newPatient);
        return newPatient;
    }

    // GET /api/patients/search?diagnosis=грипп - поиск по диагнозу
    @GetMapping("/search")
    public List<Patient> searchByDiagnosis(@RequestParam String diagnosis) {
        List<Patient> result = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getDiagnosis() != null &&
                    patient.getDiagnosis().equalsIgnoreCase(diagnosis)) {
                result.add(patient);
            }
        }
        return result;
    }

    // GET /api/patients/test - тестовый эндпоинт (как в вашей лабке)
    @GetMapping("/test")
    public String test() {
        return "Patient Controller is working!";
    }
}