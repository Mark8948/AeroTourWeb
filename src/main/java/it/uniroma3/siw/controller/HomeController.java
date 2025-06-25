package it.uniroma3.siw.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    // Endpoint per restituire l'immagine di un aereo
    @GetMapping(value = "/airplane/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAirplaneImage(@PathVariable Long id) {
        Airplane airplane = airplaneService.getAirplane(id);
        if (airplane == null || airplane.getImage() == null || airplane.getImage().length == 0) {
            // Puoi decidere di ritornare un'immagine di default o un 404
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // Cambia se usi PNG o altro formato
                .body(airplane.getImage());
    }
}
