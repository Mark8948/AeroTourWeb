package it.uniroma3.siw.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ContattiController {

    @GetMapping("/contatti")
    public String contatti(Model model) {
        return "contatti";
    }
}
