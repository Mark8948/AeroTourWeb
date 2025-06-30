package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.enums.Status;
import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.model.tables.AirplaneCustomization;
import it.uniroma3.siw.model.tables.OrderRequest;
import it.uniroma3.siw.model.tables.Users;
import it.uniroma3.siw.repository.AirplaneCustomizationRepository;
import it.uniroma3.siw.repository.AirplaneRepository;
import it.uniroma3.siw.repository.OrderRequestRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The OrderRequestService gestisce la logica per l’entità OrderRequest.
 */
@Service
public class OrderRequestService {

	@Autowired
	protected OrderRequestRepository orderRequestRepository;

	@Autowired
	protected AirplaneRepository airplaneRepository;
	
	@Autowired
	protected AirplaneCustomizationRepository customizationRepository;

	/**
	 * Recupera un OrderRequest dal DB in base al suo ID.
	 * 
	 * @param id l’ID della richiesta ordine da recuperare
	 * @return l’OrderRequest recuperato, oppure null se non esiste
	 */
	@Transactional
	public OrderRequest getOrderRequest(Long id) {
		Optional<OrderRequest> result = this.orderRequestRepository.findById(id);
		return result.orElse(null);
	}

	/**
	 * Salva un OrderRequest nel DB.
	 * 
	 * @param orderRequest l’istanza di OrderRequest da salvare
	 * @return l’OrderRequest salvato
	 * @throws DataIntegrityViolationException se violato qualche vincolo
	 */
	@Transactional
	public OrderRequest saveOrderRequest(OrderRequest orderRequest) {
		return this.orderRequestRepository.save(orderRequest);
	}

	/**
	 * Recupera tutte le OrderRequest presenti nel DB.
	 * 
	 * @return una List contenente tutte le OrderRequest
	 */
	@Transactional
	public List<OrderRequest> getAllOrderRequests() {
		List<OrderRequest> result = new ArrayList<>();
		Iterable<OrderRequest> iterable = this.orderRequestRepository.findAll();
		for (OrderRequest or : iterable) {
			result.add(or);
		}
		return result;
	}

	@Transactional
	public List<OrderRequest> findOrdersByUser(Users user) {
		return orderRequestRepository.findOrdersByUser(user);
	}

	@Transactional
	public void createOrder(Users currentUser, Long airplaneId, List<Long> customizationIds) {
		Airplane airplane = airplaneRepository.findById(airplaneId)
				.orElseThrow(() -> new IllegalArgumentException("Aereo non trovato"));

		List<AirplaneCustomization> customizations = (customizationIds != null && !customizationIds.isEmpty())
			    ? (List<AirplaneCustomization>) customizationRepository.findAllById(customizationIds)
			    : new ArrayList<>();

		OrderRequest order = new OrderRequest();
		order.setUser(currentUser);
		order.setAirplane(airplane);
		order.setCustomizations(customizations);
		order.setCreationDate(LocalDateTime.now());
		order.setStato(Status.IN_ATTESA_DI_CONFERMA);

		// Calcola e imposta il prezzo totale
		float totalPrice = airplane.getPrice();
		for (AirplaneCustomization c : customizations) {
			totalPrice += c.getModificationPrice();
		}
		order.setTotalPrice(totalPrice);
		orderRequestRepository.save(order);
	}

	/**
	 * Imposta lo stato dell'ordine su CONFERMATO.
	 */
	@Transactional
	public void confirmOrder(Long id) {
		OrderRequest order = orderRequestRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Ordine non trovato: " + id));
		order.setStato(Status.CONFERMATO);
		orderRequestRepository.save(order);
	}

	/**
	 * Imposta lo stato dell'ordine su RIFIUTATO.
	 */
	@Transactional
	public void rejectOrder(Long id) {
		OrderRequest order = orderRequestRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Ordine non trovato: " + id));
		order.setStato(Status.RIFIUTATO);
		orderRequestRepository.save(order);
	}
	
	/**
	 * Imposta lo stato dell'ordine su annullato.
	 */
	@Transactional
	public void annullaOrder(Long id) {
		OrderRequest order = orderRequestRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Ordine non trovato: " + id));
		order.setStato(Status.ANNULLATO);
		orderRequestRepository.save(order);
	}
	
	

}
