package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.tables.VisitsBooking;
import it.uniroma3.siw.repository.VisitsBookingRepository;

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
     * @throws DataIntegrityViolationException se violato qualche vincolo
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
}
