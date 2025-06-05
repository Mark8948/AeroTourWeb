package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.tables.OrderRequest;
import it.uniroma3.siw.repository.OrderRequestRepository;

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
}
