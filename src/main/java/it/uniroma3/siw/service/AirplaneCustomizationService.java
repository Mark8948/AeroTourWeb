package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.tables.AirplaneCustomization;
import it.uniroma3.siw.repository.AirplaneCustomizationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The AirplaneCustomizationService gestisce la logica per l’entità AirplaneCustomization.
 */
@Service
public class AirplaneCustomizationService {

    @Autowired
    protected AirplaneCustomizationRepository airplaneCustomizationRepository;

    /**
     * Recupera un AirplaneCustomization dal DB in base al suo ID.
     * 
     * @param id l’ID della personalizzazione da recuperare
     * @return l’AirplaneCustomization recuperato, oppure null se non esiste
     */
    @Transactional
    public AirplaneCustomization getAirplaneCustomization(Long id) {
        Optional<AirplaneCustomization> result = this.airplaneCustomizationRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * Salva un AirplaneCustomization nel DB.
     * 
     * @param customization l’istanza di AirplaneCustomization da salvare
     * @return l’AirplaneCustomization salvato
     * @throws DataIntegrityViolationException se violato qualche vincolo
     */
    @Transactional
    public AirplaneCustomization saveAirplaneCustomization(AirplaneCustomization customization) {
        return this.airplaneCustomizationRepository.save(customization);
    }

    /**
     * Recupera tutte le AirplaneCustomization presenti nel DB.
     * 
     * @return una List contenente tutte le Customization
     */
    @Transactional
    public List<AirplaneCustomization> getAllAirplaneCustomizations() {
        List<AirplaneCustomization> result = new ArrayList<>();
        Iterable<AirplaneCustomization> iterable = this.airplaneCustomizationRepository.findAll();
        for (AirplaneCustomization ac : iterable) {
            result.add(ac);
        }
        return result;
    }
}
