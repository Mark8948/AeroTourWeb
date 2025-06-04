package it.uniroma3.siw.controller;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;

// @Controller
@RestController
public class HomeController {

	// @GetMapping({"/", "index"})
	// public String home(Model model) { //Model model serve a passare i dati alla
	// home con tutti gli aerei.
	// return "homepage";
	// }

	@GetMapping("/")
	public String Home() {
		return "Hello, Home!";
	}

	@GetMapping("/secured")
	public String secured() {
		return "Hello, Secured!";
	}

}