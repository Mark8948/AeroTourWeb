package it.uniroma3.siw.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.enums.Status;
import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.model.tables.AirplaneCustomization;
import it.uniroma3.siw.model.tables.OrderRequest;
import it.uniroma3.siw.service.AirplaneService;
import it.uniroma3.siw.service.OrderRequestService;
import it.uniroma3.siw.service.VisitsBookingService;

@Controller
public class AdminPersonalAreaController {

    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private VisitsBookingService visitsBookingService;
    
    @Autowired
    private OrderRequestService orderRequestService;

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

        model.addAttribute("airplane", airplane);
        model.addAttribute("customizations", airplane.getCustomizations());
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

        if ( modIds!= null && modIds.size() > names.size()) {
            throw new IllegalArgumentException("modIds list non allineata " + modIds.size() + " " + names.size());
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
        return "/admin/removePlane";
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
    
    @GetMapping("/admin/manageOrders")
    public String showManageOrdersPage(Model model) {
        Iterable<OrderRequest> orderList = orderRequestService.getAllOrderRequests();

        // Filter orders with status IN_ATTESA_DI_CONFERMA
        List<OrderRequest> pendingOrders = StreamSupport.stream(orderList.spliterator(), false)
            .filter(o -> o.getStato() == Status.IN_ATTESA_DI_CONFERMA)
            .collect(Collectors.toList());

        model.addAttribute("orders", pendingOrders);
        
        //TODO
        
        Map<Long,  List<AirplaneCustomization>> map = new HashMap<>();
        
        for (OrderRequest order : pendingOrders) {
            List<AirplaneCustomization> customizations = order.getCustomizations();
            if (customizations == null) {
                customizations = new ArrayList<>();
            }
            map.put(order.getId(), customizations);
        }

        model.addAttribute("map", map);
        
        return "/admin/manageOrders";
    }

    
    @PostMapping("/admin/confirmOrder/{id}")
    public String confirmOrder(@PathVariable Long id) {
        orderRequestService.confirmOrder(id);
        return "redirect:/admin/manageOrders";
    }

    @PostMapping("/admin/rejectOrder/{id}")
    public String rejectOrder(@PathVariable Long id) {
        orderRequestService.rejectOrder(id);
        return "redirect:/admin/manageOrders";
    }
    
}
