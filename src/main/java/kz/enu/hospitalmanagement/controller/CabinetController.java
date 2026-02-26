package kz.enu.hospitalmanagement.controller;

import kz.enu.hospitalmanagement.entities.Cabinet;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api/cabinets")
public class CabinetController {
    private List<Cabinet> cabinets=new ArrayList<>();
    private Long nextId = 1L;
    public CabinetController(){
        cabinets.add(new Cabinet(nextId++,101,"1","Кабинет кардиолога",true));
        cabinets.add(new Cabinet(nextId++,102,"1","Кабинет терапевта",true));
    }

}
