package kz.enu.hospitalmanagement.controller;

import kz.enu.hospitalmanagement.entities.Cabinet;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cabinets")
public class CabinetController {

    private List<Cabinet> cabinets = new ArrayList<>();
    private Long nextId = 1L;

    public CabinetController() {
        // Добавим несколько кабинетов
        cabinets.add(new Cabinet(nextId++, 101, "1", "Кабинет кардиолога", true));
        cabinets.add(new Cabinet(nextId++, 102, "1", "Кабинет терапевта", true));
        cabinets.add(new Cabinet(nextId++, 205, "2", "Кабинет хирурга", true));
        cabinets.add(new Cabinet(nextId++, 206, "2", "Кабинет окулиста", false)); // временно недоступен
        cabinets.add(new Cabinet(nextId++, 310, "3", "Кабинет ЛОРа", true));
    }

    // GET /api/cabinets - все кабинеты
    @GetMapping
    public List<Cabinet> getAllCabinets() {
        return cabinets;
    }

    // GET /api/cabinets/{id} - кабинет по ID
    @GetMapping("/{id}")
    public Cabinet getCabinetById(@PathVariable Long id) {
        for (Cabinet cabinet : cabinets) {
            if (cabinet.getId().equals(id)) {
                return cabinet;
            }
        }
        return null;
    }

    // GET /api/cabinets/floor/{floor} - кабинеты на этаже
    @GetMapping("/floor/{floor}")
    public List<Cabinet> getCabinetsByFloor(@PathVariable String floor) {
        List<Cabinet> result = new ArrayList<>();
        for (Cabinet cabinet : cabinets) {
            if (cabinet.getFloor().equals(floor)) {
                result.add(cabinet);
            }
        }
        return result;
    }

    // GET /api/cabinets/available - доступные кабинеты
    @GetMapping("/available")
    public List<Cabinet> getAvailableCabinets() {
        List<Cabinet> result = new ArrayList<>();
        for (Cabinet cabinet : cabinets) {
            if (cabinet.isAvailable()) {
                result.add(cabinet);
            }
        }
        return result;
    }

    // POST /api/cabinets - создать новый кабинет
    @PostMapping
    public Cabinet createCabinet(@RequestBody Cabinet newCabinet) {
        newCabinet.setId(nextId++);
        cabinets.add(newCabinet);
        return newCabinet;
    }

    // PUT /api/cabinets/{id}/status - изменить доступность кабинета
    @PutMapping("/{id}/status")
    public Cabinet changeCabinetStatus(@PathVariable Long id, @RequestParam boolean available) {
        for (Cabinet cabinet : cabinets) {
            if (cabinet.getId().equals(id)) {
                cabinet.setAvailable(available);
                return cabinet;
            }
        }
        return null;
    }

    @GetMapping("/test")
    public String test() {
        return "Cabinet Controller is working!";
    }
}