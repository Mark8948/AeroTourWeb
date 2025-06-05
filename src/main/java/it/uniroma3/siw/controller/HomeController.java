package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
	
	@GetMapping({"/", "home"})
	public String home(Model model) { //Model model serve a passare i dati alla home con tutti gli aerei.
			return "homepage";
	}
}