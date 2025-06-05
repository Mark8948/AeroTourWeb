package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.tables.Airplane;
import it.uniroma3.siw.repository.AirplaneRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The AirplaneService gestisce la logica per l’entità Airplane.
 */
@Service
public class AirplaneService {

    @Autowired
    protected AirplaneRepository airplaneRepository;

    /**
     * Recupera un Airplane dal DB in base al suo ID.
     * 
     * @param id l’ID dell’Airplane da recuperare
     * @return l’Airplane recuperato, oppure null se non esiste
     */
    @Transactional
    public Airplane getAirplane(Long id) {
        Optional<Airplane> result = this.airplaneRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * Salva un Airplane nel DB.
     * 
     * @param airplane l’istanza di Airplane da salvare
     * @return l’Airplane salvato
     * @throws DataIntegrityViolationException se violato qualche vincolo (es. codice univoco già esistente)
     */
    @Transactional
    public Airplane saveAirplane(Airplane airplane) {
        return this.airplaneRepository.save(airplane);
    }

    /**
     * Recupera tutti gli Airplane presenti nel DB.
     * 
     * @return una List contenente tutti gli Airplane
     */
    @Transactional
    public List<Airplane> getAllAirplanes() {
        List<Airplane> result = new ArrayList<>();
        Iterable<Airplane> iterable = this.airplaneRepository.findAll();
        for (Airplane a : iterable) {
            result.add(a);
        }
        return result;
    }
}
