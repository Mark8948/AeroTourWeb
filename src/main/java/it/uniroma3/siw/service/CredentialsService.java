package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.tables.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The CredentialsService gestisce la logica per l’entità Credentials.
 */
@Service
public class CredentialsService {

    @Autowired
    protected CredentialsRepository credentialsRepository;

    /**
     * Recupera un Credentials dal DB in base al suo ID.
     * 
     * @param id l’ID delle credenziali da recuperare
     * @return le Credentials recuperate, oppure null se non esistono
     */
    @Transactional
    public Credentials getCredentials(Long id) {
        Optional<Credentials> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * Salva un Credentials nel DB.
     * 
     * @param credentials l’istanza di Credentials da salvare
     * @return le Credentials salvate
     * @throws DataIntegrityViolationException se violato qualche vincolo (es. username o email univoci)
     */
    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        return this.credentialsRepository.save(credentials);
    }

    /**
     * Recupera tutte le Credentials presenti nel DB.
     * 
     * @return una List contenente tutte le Credentials
     */
    @Transactional
    public List<Credentials> getAllCredentials() {
        List<Credentials> result = new ArrayList<>();
        Iterable<Credentials> iterable = this.credentialsRepository.findAll();
        for (Credentials c : iterable) {
            result.add(c);
        }
        return result;
    }
}
