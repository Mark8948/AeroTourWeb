package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.service.AirplaneService;

@Controller
public class AirplaneImageController {

    @Autowired
    private AirplaneService airplaneService;

    @GetMapping(value = "/airplane/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAirplaneImage(@PathVariable Long id) {
        Airplane airplane = airplaneService.getAirplane(id);
        if (airplane == null || airplane.getImage() == null || airplane.getImage().length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(airplane.getImage());
    }
}
