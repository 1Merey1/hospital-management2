package kz.enu.hospitalmanagement.controller;

import kz.enu.hospitalmanagement.entities.Patient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private List<Patient> patients = new ArrayList<>();
    private Long nextId = 1L;

    public PatientController() {
        // Добавим пациентов с привязкой к докторам
        patients.add(new Patient(nextId++, "Иван", "Петров", 35, "Грипп",
                "+7-777-111-1111", 1L)); // лечит доктор Петров (кардиолог?)
        patients.add(new Patient(nextId++, "Мария", "Сидорова", 28, "Ангина",
                "+7-777-222-2222", 2L)); // лечит доктор Иванова (терапевт)
        patients.add(new Patient(nextId++, "Алексей", "Иванов", 42, "Перелом",
                "+7-777-333-3333", 3L)); // лечит доктор Алиев (хирург)
        patients.add(new Patient(nextId++, "Гульнара", "Касымова", 55, "Катаракта",
                "+7-777-444-4444", 4L)); // лечит доктор Нурланова (окулист)
    }

    // GET /api/patients - все пациенты
    @GetMapping
    public List<Patient> getAllPatients() {
        return patients;
    }

    // GET /api/patients/{id} - пациент по ID
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }

    // GET /api/patients/doctor/{doctorId} - пациенты конкретного доктора
    @GetMapping("/doctor/{doctorId}")
    public List<Patient> getPatientsByDoctor(@PathVariable Long doctorId) {
        List<Patient> result = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getDoctorId() != null && patient.getDoctorId().equals(doctorId)) {
                result.add(patient);
            }
        }
        return result;
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

    // POST /api/patients - создать пациента
    @PostMapping
    public Patient createPatient(@RequestBody Patient newPatient) {
        newPatient.setId(nextId++);
        patients.add(newPatient);
        return newPatient;
    }

    // PUT /api/patients/{id}/doctor - назначить доктора пациенту
    @PutMapping("/{id}/doctor")
    public Patient assignDoctor(@PathVariable Long id, @RequestParam Long doctorId) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                patient.setDoctorId(doctorId);
                return patient;
            }
        }
        return null;
    }

    @GetMapping("/test")
    public String test() {
        return "Patient Controller is working!";
    }
}