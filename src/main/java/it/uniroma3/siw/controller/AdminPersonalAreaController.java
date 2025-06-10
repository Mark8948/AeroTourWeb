package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.repository.AirplaneRepository;

@Controller
public class AdminPersonalAreaController {
	
	@Autowired
	private AirplaneRepository airplaneRepository;

	// Pagina di dashboard admin
	@GetMapping("/admin")
	public String adminDashboard() {
		return "adminPersonalArea"; // Nessun oggetto airplane necessario qui
	}

	
	//AREA GESTIONE AEREI
	
	
	// Pagina per mostrare il form
	@GetMapping("/admin/insertPlane")
	public String showInsertPlaneForm(Model model) {
		model.addAttribute("airplane", new Airplane());
		return "admin/insertPlane";
	}

	// Gestione POST del form
	@PostMapping("admin/airplanes")
	public String saveAirplane(@ModelAttribute("airplane") Airplane airplane, RedirectAttributes redirectAttributes) {
	    Airplane savedAirplane = airplaneRepository.save(airplane);
	    redirectAttributes.addFlashAttribute("airplaneInsertionSuccessMessage", "Inserimento dell'aereo riuscito, con il nome: " + savedAirplane.getModelName() + " [id: " + savedAirplane.getId() + "]");
	    return "redirect:/admin/insertPlane";
	}
	
	@GetMapping("/admin/removePlane")
	public String showRemovePlanePage(Model model) {
	    model.addAttribute("airplanes", airplaneRepository.findAll());
	    return "admin/removePlane";
	}
	
	@GetMapping("/admin/removePlane/{id}")
	public String removePlane(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
	    airplaneRepository.deleteById(id);
	    redirectAttributes.addFlashAttribute("airplaneRemovalSuccessMessage", "Aereo rimosso con successo [ID: " + id + "]");
	    return "redirect:/admin/removePlane";
	}

	
	
	//AREA GESTIONE VISITE
	
	
	
	
	
	
	//AREA GESTIONE ACQUISTO AEREI

}
