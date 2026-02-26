package kz.enu.hospitalmanagement.controller;

import kz.enu.hospitalmanagement.entities.Doctor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private List<Doctor> doctors = new ArrayList<>();
    private Long nextId = 1L;

    public DoctorController() {
        // Добавим докторов с привязкой к кабинетам
        doctors.add(new Doctor(nextId++, "Сергей", "Петров", "Кардиолог",
                "+7-701-111-1111", "s.petrov@hospital.kz", 1L)); // кабинет 101
        doctors.add(new Doctor(nextId++, "Анна", "Иванова", "Терапевт",
                "+7-701-222-2222", "a.ivanova@hospital.kz", 2L)); // кабинет 102
        doctors.add(new Doctor(nextId++, "Марат", "Алиев", "Хирург",
                "+7-701-333-3333", "m.aliev@hospital.kz", 3L)); // кабинет 205
        doctors.add(new Doctor(nextId++, "Динара", "Нурланова", "Окулист",
                "+7-701-444-4444", "d.nurlanova@hospital.kz", 4L)); // кабинет 206
    }

    // GET /api/doctors - все доктора
    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    // GET /api/doctors/{id} - доктор по ID
    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(id)) {
                return doctor;
            }
        }
        return null;
    }

    // GET /api/doctors/cabinet/{cabinetId} - доктор по кабинету
    @GetMapping("/cabinet/{cabinetId}")
    public Doctor getDoctorByCabinet(@PathVariable Long cabinetId) {
        for (Doctor doctor : doctors) {
            if (doctor.getCabinetId() != null && doctor.getCabinetId().equals(cabinetId)) {
                return doctor;
            }
        }
        return null;
    }

    // GET /api/doctors/specialization/{spec} - доктора по специализации
    @GetMapping("/specialization/{spec}")
    public List<Doctor> getDoctorsBySpecialization(@PathVariable String spec) {
        List<Doctor> result = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getSpecialization() != null &&
                    doctor.getSpecialization().equalsIgnoreCase(spec)) {
                result.add(doctor);
            }
        }
        return result;
    }

    // POST /api/doctors - создать доктора
    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor newDoctor) {
        newDoctor.setId(nextId++);
        doctors.add(newDoctor);
        return newDoctor;
    }

    // PUT /api/doctors/{id}/cabinet - назначить кабинет доктору
    @PutMapping("/{id}/cabinet")
    public Doctor assignCabinet(@PathVariable Long id, @RequestParam Long cabinetId) {
        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(id)) {
                doctor.setCabinetId(cabinetId);
                return doctor;
            }
        }
        return null;
    }

    @GetMapping("/test")
    public String test() {
        return "Doctor Controller is working!";
    }
}