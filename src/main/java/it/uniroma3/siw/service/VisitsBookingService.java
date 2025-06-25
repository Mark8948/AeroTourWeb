package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.enums.Status;
import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.model.tables.Users;
import it.uniroma3.siw.model.tables.VisitsBooking;
import it.uniroma3.siw.repository.AirplaneRepository;
//import it.uniroma3.siw.repository.UsersRepository;
import it.uniroma3.siw.repository.VisitsBookingRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitsBookingService {

	@Autowired
	private VisitsBookingRepository visitsBookingRepository;

	@Autowired
	private AirplaneRepository airplaneRepository;

	// @Autowired
	// private UsersRepository usersRepository;

	/**
	 * Recupera un VisitsBooking dal DB in base al suo ID.
	 * 
	 * @param id l’ID della prenotazione da recuperare
	 * @return il VisitsBooking trovato
	 * @throws IllegalArgumentException se non esiste
	 */
	@Transactional(readOnly = true)
	public VisitsBooking getVisitsBooking(Long id) {
		return visitsBookingRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("VisitsBooking non trovato: id=" + id));
	}

	/**
	 * Salva un VisitsBooking nel DB.
	 * 
	 * @param booking l’istanza di VisitsBooking da salvare
	 * @return il VisitsBooking salvato
	 */
	@Transactional
	public VisitsBooking saveVisitsBooking(VisitsBooking booking) {
		return visitsBookingRepository.save(booking);
	}

	/**
	 * Recupera tutte le prenotazioni VisitsBooking presenti nel DB.
	 * 
	 * @return List di tutte le VisitsBooking
	 */
	@Transactional(readOnly = true)
	public List<VisitsBooking> getAllVisitsBookings() {
		return visitsBookingRepository.findAll();
	}

	/**
	 * Prenota una visita per l'utente dato e l'aereo specificato.
	 * 
	 * @param user       l’utente che prenota
	 * @param airplaneId l’ID dell’aereo da visitare
	 */
	@Transactional
	public void bookVisit(Users user, Long airplaneId) {
		Airplane airplane = airplaneRepository.findById(airplaneId)
				.orElseThrow(() -> new IllegalArgumentException("Aereo non trovato: id=" + airplaneId));

		VisitsBooking booking = new VisitsBooking();
		booking.setUser(user);
		booking.setAirplane(airplane);
		booking.setStatus(Status.IN_ATTESA_DI_CONFERMA);

		visitsBookingRepository.save(booking);
	}

	/**
	 * Recupera tutte le prenotazioni di un dato utente.
	 * 
	 * @param user l’utente di cui cercare le prenotazioni
	 * @return List di VisitsBooking associati all’utente
	 */
	@Transactional(readOnly = true)
	public List<VisitsBooking> findByUser(Users user) {
		return visitsBookingRepository.findByUser(user);
	}

	/**
	 * Cambia lo stato della prenotazione in ANNULLATO
	 * 
	 * @param il booking id
	 */
	@Transactional
	public void cancelBooking(Long bookingId) {
		VisitsBooking booking = visitsBookingRepository.findById(bookingId)
				.orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata: id=" + bookingId));

		booking.setStatus(Status.ANNULLATO);
		visitsBookingRepository.save(booking);
	}

	/**
	 * Recupera tutte le prenotazioni VisitsBooking presenti nel DB.
	 *
	 * @return List di tutte le VisitsBooking
	 */
	public List<VisitsBooking> findAll() {
		return visitsBookingRepository.findAll();
	}

	public void rejectBooking(Long id) {
		
		VisitsBooking booking = visitsBookingRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata: id=" + id));

		booking.setStatus(Status.RIFIUTATO);
		visitsBookingRepository.save(booking);
	}

	@Transactional
	public void confirmBooking(Long bookingId,
	                           String guideSurname,
	                           String guidePhone,
	                           LocalDateTime confirmedDateTime) {
	    VisitsBooking booking = visitsBookingRepository.findById(bookingId)
	        .orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata: id=" + bookingId));

	    booking.setStatus(Status.CONFERMATO);
	    booking.setGuideSurname(guideSurname);
	    booking.setGuidePhone(guidePhone);
	    
	    booking.setVisitDateTime(confirmedDateTime);

	    visitsBookingRepository.save(booking);
	}

}
