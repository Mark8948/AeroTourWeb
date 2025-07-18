package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.enums.Status;
import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.model.tables.AirplaneCustomization;
import it.uniroma3.siw.model.tables.OrderRequest;
import it.uniroma3.siw.model.tables.Users;
import it.uniroma3.siw.model.tables.VisitsBooking;
import it.uniroma3.siw.service.AirplaneService;
import it.uniroma3.siw.service.OrderRequestService;
import it.uniroma3.siw.service.UsersService;
import it.uniroma3.siw.service.VisitsBookingService;

@Controller
public class UserPersonalAreaController {

	@Autowired
	private AirplaneService airplaneService;

	@Autowired
	private VisitsBookingService visitsBookingService;

	@Autowired
	private UsersService usersService;
	
	@Autowired 
	private OrderRequestService orderRequestService;

	@GetMapping("user")
	public String home() {
		return "userPersonalArea";
	}

	/**
	 * Pagina per creare un nuovo ordine
	 */
	@GetMapping("/user/newOrder")
	public String newOrder(Model model) {
		Iterable<Airplane> airplanes = airplaneService.getAllAirplanes();
		model.addAttribute("airplanes", airplanes);
		
		Map<Long, List<AirplaneCustomization>> map = new HashMap<>();
		for(Airplane plane: airplanes) {
			map.put( plane.getId(), plane.getCustomizations());
			//System.out.println(plane.getId() + " : " + plane.getCustomizations());
		} 
		model.addAttribute("customizationsMap", map);
		return "user/userNewOrder";
	}
	
	
	@PostMapping("/user/orderPlane/{id}")
	public String saveOrder(
	    @PathVariable("id") Long id,
	    @RequestParam(name = "customizationIds", required = false) List<Long> customizationIds) {

	    Users currentUser = usersService.getCurrentUser();

	    if (customizationIds == null) {
	        customizationIds = new ArrayList<>();
	    }

	    //for(long idc :customizationIds )
	    	//System.out.println(idc);
	    
	    orderRequestService.createOrder(currentUser, id, customizationIds);

	    return "redirect:/user/orders";
	}

    /** Visualizza la cronologia ordini dell'utente */
    
    @GetMapping("/user/orders")
    public String orderHistory(Model model) {
        Users currentUser = usersService.getCurrentUser();
        List<OrderRequest> orders = orderRequestService.findOrdersByUser(currentUser);
        Map<Long,  List<AirplaneCustomization>> map = new HashMap<>();
        
        for (OrderRequest order : orders) {
            List<AirplaneCustomization> customizations = order.getCustomizations();
            if (customizations == null) {
                customizations = new ArrayList<>();
            }
            map.put(order.getId(), customizations);
            
            //for(AirplaneCustomization i : map.get(order.getId()))
            //System.out.println(i.getModificationName());
        }

        model.addAttribute("orders", orders);
        model.addAttribute("map", map);
        return "user/userOrders";
    }

	/**
	 * Mostra il form per prenotare una visita con la lista di aerei
	 */
	
	@GetMapping("/user/bookVisit")
	public String showVisitBookingForm(Model model) {
		Iterable<Airplane> airplanes = airplaneService.getAllAirplanes();
		model.addAttribute("airplanes", airplanes);
		model.addAttribute("bookingForm", new VisitsBooking());
		return "user/userVisitBookingForm";
	}

	@PostMapping("/user/bookVisit")
	public String submitVisitsBooking(@RequestParam("airplaneId") Long airplaneId,
	                                  Model model) {

	    Users currentUser = usersService.getCurrentUser();

	    // Recupera tutte le prenotazioni dell’utente per quell’aereo
	    List<VisitsBooking> existingBookings = visitsBookingService.findByUser(currentUser).stream()
	        .filter(b -> b.getAirplane().getId().equals(airplaneId))
	        .filter(b -> b.getStatus() != Status.RIFIUTATO && b.getStatus() != Status.ANNULLATO)
	        .toList();

	    if (!existingBookings.isEmpty()) {
	        model.addAttribute("errorMessage", "Hai già una prenotazione attiva per questo aereo.");
	        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
	        model.addAttribute("bookingForm", new VisitsBooking());
	        return "user/userVisitBookingForm";
	    }

	    visitsBookingService.bookVisit(currentUser, airplaneId);
	    return "redirect:/user/bookings";
	}

    @GetMapping("/user/bookings")
    public String viewBookings(Model model, @AuthenticationPrincipal OAuth2User principal) {
        //Users currentUser = usersService.getOrCreateUser(principal);
    	Users currentUser = usersService.getCurrentUser();
    	
    	List<VisitsBooking> bookings = visitsBookingService.findByUser(currentUser);
        model.addAttribute("bookings", bookings);
        return "user/userVisitsBooked";
    }

    
    @PostMapping("/user/cancelBooking/{id}")
    public String cancelBooking(@PathVariable("id") Long id) {
        visitsBookingService.cancelBooking(id);
        return "redirect:/user/bookings";
    }
    
    
    @PostMapping("/user/invalidateOrder/{id}")
    public String annullaOrder(@PathVariable Long id) {
        orderRequestService.annullaOrder(id);
        return "redirect:/user/orders";
    }


}
