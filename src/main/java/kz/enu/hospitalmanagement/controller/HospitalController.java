package kz.enu.hospitalmanagement.controller;

import kz.enu.hospitalmanagement.entities.Hospital;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospital")
public class HospitalController {

    // GET /api/hospital - получить информацию о больнице
    @GetMapping
    public Hospital getHospital() {
        return Hospital.getInstance();
    }

    // PUT /api/hospital - обновить информацию о больнице
    @PutMapping
    public Hospital updateHospital(@RequestBody Hospital updatedHospital) {
        Hospital hospital = Hospital.getInstance();
        hospital.setName(updatedHospital.getName());
        hospital.setAddress(updatedHospital.getAddress());
        hospital.setPhoneNumber(updatedHospital.getPhoneNumber());
        hospital.setEmail(updatedHospital.getEmail());
        return hospital;
    }

    // GET /api/hospital/doctors - получить всех докторов больницы
    // (этот эндпоинт будет возвращать докторов, но мы его реализуем позже)

    @GetMapping("/test")
    public String test() {
        return "Hospital Controller is working!";
    }
}