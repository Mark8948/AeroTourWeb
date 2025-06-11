package it.uniroma3.siw.controller;

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
    
    @GetMapping("/admin/editPlane/{id}")
    public String showEditPlaneForm(@PathVariable Long id, Model model) {
        Airplane airplane = airplaneService.getAirplane(id);
        model.addAttribute("airplane", airplane);
        return "admin/insertPlane";  // stesso template del form di inserimento
    }
    
    @PostMapping("/admin/airplanes/{id}")
    public String updateAirplane(
            @PathVariable Long id,
            @ModelAttribute("airplane") Airplane airplane,
            @RequestParam(value = "modificationNames",        required = false) List<String> names,
            @RequestParam(value = "modificationDescriptions", required = false) List<String> descs,
            @RequestParam(value = "modificationDates",        required = false) List<String> dates,
            @RequestParam(value = "modificationPrices",       required = false) List<Float>  prices,
            @RequestParam(value = "modificationUrls",         required = false) List<String> urls,
            RedirectAttributes redirectAttributes) {

        // 1) Imposta l’ID per forzare l’update anziché insert
        airplane.setId(id);

        // 2) Validazione delle liste di customizzazioni
        if (names != null) {
            int count = names.size();
            if ((descs == null  || descs.size()  < count) ||
                (dates == null  || dates.size()  < count) ||
                (prices == null || prices.size() < count) ||
                (urls == null   || urls.size()   < count)) {
                throw new IllegalArgumentException("Liste delle modifiche incoerenti");
            }
        }

        // 3) Salvo con customizzazioni
        Airplane updated = airplaneService.saveAirplaneWithCustomizations(
            airplane, names, descs, dates, prices, urls
        );

        // 4) Aggiungo messaggio di conferma
        redirectAttributes.addFlashAttribute("airplaneInsertionSuccessMessage",
            "Aereo modificato con successo: " 
            + updated.getModelName() 
            + " [ID: " + updated.getId() + "]"
        );

        // 5) Redirect alla pagina di modifica dello stesso aereo
        return "redirect:/admin/editPlane/" + updated.getId();
    }



    // Salvataggio aereo con eventuali personalizzazioni
    @PostMapping("/admin/airplanes")
    public String saveAirplane(
            @ModelAttribute("airplane") Airplane airplane,
            @RequestParam(value = "modificationNames",        required = false) List<String> names,
            @RequestParam(value = "modificationDescriptions", required = false) List<String> descs,
            @RequestParam(value = "modificationDates",        required = false) List<String> dates,
            @RequestParam(value = "modificationPrices",       required = false) List<Float>  prices,
            @RequestParam(value = "modificationUrls",         required = false) List<String> urls,
            RedirectAttributes redirectAttributes) {

        // Calcola quanti set completi di campi hai
        int count = 0;
        if (names != null) {
            count = names.size();
            // verifica che tutte le altre liste abbiano almeno 'count' elementi
            if ((descs == null || descs.size() < count)
             || (dates == null || dates.size() < count)
             || (prices == null || prices.size() < count)
             || (urls   == null || urls.size()   < count)) {
                throw new IllegalArgumentException("Liste delle modifiche incoerenti");
            }
        }

        Airplane saved = airplaneService.saveAirplaneWithCustomizations(
            airplane,
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
