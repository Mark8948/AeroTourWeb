package it.uniroma3.siw.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.service.AirplaneService;

@Controller
public class HomeController {

    @Autowired
    private AirplaneService airplaneService;

    @GetMapping("/")
    public String homepage(Model model, Principal principal) {
        // 1) Lista aerei
        List<Airplane> airplanes = airplaneService.getAllAirplanes();
        model.addAttribute("airplanes", airplanes);
        
        return "homepage";
    }

    
}
