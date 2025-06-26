package it.uniroma3.siw.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.model.tables.AirplaneCustomization;
import it.uniroma3.siw.service.AirplaneService;
import it.uniroma3.siw.service.VisitsBookingService;

@Controller
public class AdminPersonalAreaController {

    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private VisitsBookingService visitsBookingService;

    @GetMapping("/admin")
    public String adminDashboard() {
        return "adminPersonalArea";
    }

    @GetMapping("/admin/insertPlane")
    public String showInsertPlaneForm(Model model) {
        model.addAttribute("airplane", new Airplane());
        return "admin/insertPlane";
    }

    @GetMapping("/admin/editPlanes")
    public String showEditPlanesList(Model model,
                                     @ModelAttribute("airplaneUpdateSuccessMessage") String msg) {
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        if (msg != null && !msg.isEmpty()) {
            model.addAttribute("airplaneUpdateSuccessMessage", msg);
        }
        return "admin/editPlane";
    }

    @GetMapping("/admin/editPlane/{id}")
    public String showEditPlaneForm(@PathVariable Long id, Model model) {
        Airplane airplane = airplaneService.getAirplane(id);

        // Defensive call to ensure the list is initialized
        if (airplane.getCustomizations() == null) {
            airplane.setCustomizations(new ArrayList<>());
        } else {
			// Trigger lazy loading to avoid Thymeleaf NullPointerException
        	System.out.println(airplane.getCustomizations().size());
        	
        	for (AirplaneCustomization mod : airplane.getCustomizations()) {
        	    System.out.println(mod);
        	}
        	
        	airplane.getCustomizations().size();
        }

        model.addAttribute("airplane", airplane);
        return "admin/editPlaneForm";
    }


    @PostMapping("/admin/editPlane/{id}")
    public String updateAirplane(
            @PathVariable Long id,
            @ModelAttribute("airplane") Airplane airplane,
            @RequestParam(value = "modificationIds", required = false) List<Long> modIds,
            @RequestParam(value = "modificationNames", required = false) List<String> names,
            @RequestParam(value = "modificationDescriptions", required = false) List<String> descs,
            @RequestParam(value = "modificationDates", required = false) List<String> dates,
            @RequestParam(value = "modificationPrices", required = false) List<Float> prices,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes) throws IOException {

        airplane.setId(id);

        // Fetch existing airplane from DB
        Airplane existingAirplane = airplaneService.getAirplane(id);

        if (names != null) {
            int count = names.size();
            if ((modIds != null && modIds.size() != count) ||
                (descs == null || descs.size() < count) ||
                (dates == null || dates.size() < count) ||
                (prices == null || prices.size() < count)) {
                throw new IllegalArgumentException("Liste delle modifiche incoerenti o incomplete.");
            }
        }

        // If a new image was uploaded, update it; else keep the old one
        if (imageFile != null && !imageFile.isEmpty()) {
            airplane.setImage(imageFile.getBytes());
        } else {
            airplane.setImage(existingAirplane.getImage());
        }

        Airplane updated = airplaneService.saveAirplaneWithCustomizations(
                airplane,
                modIds,
                names,
                descs,
                dates,
                prices
        );

        redirectAttributes.addFlashAttribute("airplaneUpdateSuccessMessage",
                "Aereo aggiornato con successo: " + updated.getModelName() + " [ID: " + updated.getId() + "]");

        return "redirect:/admin/editPlanes";
    }

    @PostMapping("/admin/airplanes")
    public String saveAirplane(
            @ModelAttribute("airplane") Airplane airplane,
            @RequestParam(value = "modificationIds", required = false) List<Long> modificationIds,
            @RequestParam(value = "modificationNames", required = false) List<String> names,
            @RequestParam(value = "modificationDescriptions", required = false) List<String> descs,
            @RequestParam(value = "modificationDates", required = false) List<String> dates,
            @RequestParam(value = "modificationPrices", required = false) List<Float> prices,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes) throws IOException {

        int count = 0;
        if (names != null) {
            count = names.size();
            if ((descs == null || descs.size() < count)
             || (dates == null || dates.size() < count)
             || (prices == null || prices.size() < count)) {
                throw new IllegalArgumentException("Liste delle modifiche incoerenti");
            }
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            airplane.setImage(imageFile.getBytes());
        }

        Airplane saved = airplaneService.saveAirplaneWithCustomizations(
            airplane,
            modificationIds,
            names, descs, dates, prices
        );

        redirectAttributes.addFlashAttribute("airplaneInsertionSuccessMessage",
            "Inserimento riuscito: " + saved.getModelName() + " [ID:" + saved.getId() + "]");
        return "redirect:/admin/insertPlane";
    }

    @GetMapping("/admin/removePlane")
    public String showRemovePlanePage(Model model) {
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        return "admin/removePlane";
    }

    @GetMapping("/admin/removePlane/{id}")
    public String removePlane(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        airplaneService.deleteAirplane(id);
        redirectAttributes.addFlashAttribute("airplaneRemovalSuccessMessage", "Aereo rimosso con successo [ID: " + id + "]");
        return "redirect:/admin/removePlane";
    }

    @GetMapping("/admin/manageVisits")
    public String manageVisits(Model model) {
        model.addAttribute("bookings", visitsBookingService.findAll());
        return "/admin/manageVisits";
    }

    @PostMapping("/admin/confirmBooking/{id}")
    public String confirmVisit(@PathVariable("id") Long id,
                               @RequestParam("guideSurname") String guideSurname,
                               @RequestParam("guidePhone") String guidePhone,
                               @RequestParam("confirmedDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime confirmedDateTime) {
        visitsBookingService.confirmBooking(id, guideSurname, guidePhone, confirmedDateTime);
        return "redirect:/admin/manageVisits";
    }

    @PostMapping("/admin/rejectBooking/{id}")
    public String rejectVisit(@PathVariable("id") Long id) {
        visitsBookingService.rejectBooking(id);
        return "redirect:/admin/manageVisits";
    }
}
