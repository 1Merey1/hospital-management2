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
        doctors.add(new Doctor(nextId++, "Сергей", "Петров", "Кардиолог", "+7-701-111-1111", "s.petrov@hospital.kz"));
        doctors.add(new Doctor(nextId++, "Анна", "Иванова", "Терапевт", "+7-701-222-2222", "a.ivanova@hospital.kz"));
        doctors.add(new Doctor(nextId++, "Марат", "Алиев", "Хирург", "+7-701-333-3333", "m.aliev@hospital.kz"));
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(id)) {
                return doctor;
            }
        }
        return null;
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor newDoctor) {
        newDoctor.setId(nextId++);
        doctors.add(newDoctor);
        return newDoctor;
    }

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

    @GetMapping("/test")
    public String test() {
        return "Doctor Controller is working!";
    }
}