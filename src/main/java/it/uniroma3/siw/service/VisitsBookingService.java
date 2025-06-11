package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.enums.Status;
import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.model.tables.Users;
import it.uniroma3.siw.model.tables.VisitsBooking;
import it.uniroma3.siw.repository.AirplaneRepository;
import it.uniroma3.siw.repository.UsersRepository;
import it.uniroma3.siw.repository.VisitsBookingRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The VisitsBookingService gestisce la logica per l’entità VisitsBooking.
 */
@Service
public class VisitsBookingService {

    @Autowired
    protected VisitsBookingRepository visitsBookingRepository;
    
    //@Autowired
    //private VisitsBookingService visitsBookingService;

    @Autowired
    protected AirplaneRepository airplaneRepository;
    
    @Autowired
    protected UsersRepository usersRepository;
    
    @Autowired
    private UsersService userService;

    /**
     * Recupera un VisitsBooking dal DB in base al suo ID.
     * 
     * @param id l’ID della prenotazione da recuperare
     * @return il VisitsBooking recuperato, oppure null se non esiste
     */
    @Transactional
    public VisitsBooking getVisitsBooking(Long id) {
        Optional<VisitsBooking> result = this.visitsBookingRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * Salva un VisitsBooking nel DB.
     * 
     * @param booking l’istanza di VisitsBooking da salvare
     * @return il VisitsBooking salvato
     */
    @Transactional
    public VisitsBooking saveVisitsBooking(VisitsBooking booking) {
        return this.visitsBookingRepository.save(booking);
    }

    /**
     * Recupera tutte le prenotazioni VisitsBooking presenti nel DB.
     * 
     * @return una List contenente tutte le VisitsBooking
     */
    @Transactional
    public List<VisitsBooking> getAllVisitsBookings() {
        List<VisitsBooking> result = new ArrayList<>();
        Iterable<VisitsBooking> iterable = this.visitsBookingRepository.findAll();
        for (VisitsBooking vb : iterable) {
            result.add(vb);
        }
        return result;
    }



    @Transactional
    public void bookVisit(Users user, Long airplaneId) {
        Airplane airplane = airplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid airplane ID"));
        
        VisitsBooking booking = new VisitsBooking();
        booking.setUser(user);
        booking.setAirplane(airplane);
        booking.setStatus(Status.IN_ATTESA_DI_CONFERMA);

        visitsBookingRepository.save(booking);
    }

    @Transactional
    public List<VisitsBooking> findByUser(Users currentUser) {
        return visitsBookingRepository.findByUser(currentUser);
    }
    
   

}
