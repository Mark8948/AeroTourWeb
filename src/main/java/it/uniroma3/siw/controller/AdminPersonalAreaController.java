package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.service.AirplaneService;

@Controller
public class AdminPersonalAreaController {

    @Autowired
    private AirplaneService airplaneService;

    // Dashboard admin
    @GetMapping("/admin")
    public String adminDashboard() {
        return "adminPersonalArea";
    }

    // Form inserimento aereo
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
        return "admin/editPlane"; // usa la pagina editPlane.html
    }
    
    
    // Mostra form modifica aereo
    @GetMapping("/admin/editPlane/{id}")
    public String showEditPlaneForm(@PathVariable Long id, Model model) {
        Airplane airplane = airplaneService.getAirplane(id);

        // Assicurati che la lista customizations non sia null
        if (airplane.getCustomizations() == null) {
            airplane.setCustomizations(new ArrayList<>());
        }

        model.addAttribute("airplane", airplane);
        return "admin/editPlaneForm";  // la pagina di modifica singola aereo
    }





    
    @PostMapping("/editPlane/{id}")
    public String updateAirplane(
            @PathVariable Long id,
            @ModelAttribute("airplane") Airplane airplane,
            @RequestParam(value = "modificationIds", required = false) List<Long> modIds,
            @RequestParam(value = "modificationNames", required = false) List<String> names,
            @RequestParam(value = "modificationDescriptions", required = false) List<String> descs,
            @RequestParam(value = "modificationDates", required = false) List<String> dates,
            @RequestParam(value = "modificationPrices", required = false) List<Float> prices,
            @RequestParam(value = "modificationUrls", required = false) List<String> urls,
            RedirectAttributes redirectAttributes) {

        airplane.setId(id);

        // Validazione delle liste delle modifiche: devono essere tutte presenti e della stessa dimensione se non null
        if (names != null) {
            int count = names.size();
            if ((modIds != null && modIds.size() != count) ||
                (descs == null || descs.size() < count) ||
                (dates == null || dates.size() < count) ||
                (prices == null || prices.size() < count) ||
                (urls == null || urls.size() < count)) {
                throw new IllegalArgumentException("Liste delle modifiche incoerenti o incomplete.");
            }
        }

        // Chiamata al servizio per salvare l'aereo e le personalizzazioni aggiornate
        Airplane updated = airplaneService.saveAirplaneWithCustomizations(
                airplane,
                modIds,        // aggiunto per aggiornare modifiche esistenti con id
                names,
                descs,
                dates,
                prices,
                urls
        );

        redirectAttributes.addFlashAttribute("airplaneUpdateSuccessMessage",
                "Aereo aggiornato con successo: " + updated.getModelName() + " [ID: " + updated.getId() + "]");

        return "redirect:/admin/editPlanes";
    }




    // Salvataggio aereo con eventuali personalizzazioni
    @PostMapping("/admin/airplanes")
    public String saveAirplane(
            @ModelAttribute("airplane") Airplane airplane,
            @RequestParam(value = "modificationIds",          required = false) List<Long> modificationIds,
            @RequestParam(value = "modificationNames",        required = false) List<String> names,
            @RequestParam(value = "modificationDescriptions", required = false) List<String> descs,
            @RequestParam(value = "modificationDates",        required = false) List<String> dates,
            @RequestParam(value = "modificationPrices",       required = false) List<Float>  prices,
            @RequestParam(value = "modificationUrls",         required = false) List<String> urls,
            RedirectAttributes redirectAttributes) {

        int count = 0;
        if (names != null) {
            count = names.size();
            if ((descs == null || descs.size() < count)
             || (dates == null || dates.size() < count)
             || (prices == null || prices.size() < count)
             || (urls == null || urls.size() < count)) {
                throw new IllegalArgumentException("Liste delle modifiche incoerenti");
            }
            // Facoltativo: puoi anche controllare che modificationIds abbia count elementi o sia null
        }

        Airplane saved = airplaneService.saveAirplaneWithCustomizations(
            airplane,
            modificationIds,
            names, descs, dates, prices, urls
        );

        redirectAttributes.addFlashAttribute("airplaneInsertionSuccessMessage",
            "Inserimento riuscito: " + saved.getModelName() + " [ID:" + saved.getId() + "]");
        return "redirect:/admin/insertPlane";
    }



    // Pagina per rimuovere un aereo
    @GetMapping("/admin/removePlane")
    public String showRemovePlanePage(Model model) {
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        return "admin/removePlane";
    }

    // Rimozione aereo
    @GetMapping("/admin/removePlane/{id}")
    public String removePlane(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        airplaneService.deleteAirplane(id);
        redirectAttributes.addFlashAttribute("airplaneRemovalSuccessMessage", "Aereo rimosso con successo [ID: " + id + "]");
        return "redirect:/admin/removePlane";
    }
}
