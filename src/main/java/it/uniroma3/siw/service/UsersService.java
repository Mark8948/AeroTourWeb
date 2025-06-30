package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import it.uniroma3.siw.model.enums.Roles;
import it.uniroma3.siw.model.tables.Credentials;
import it.uniroma3.siw.model.tables.Users;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UsersRepository;

//import java.util.ArrayList;
//import java.util.List;
import java.util.Optional;

/**
 * The UsersService handles logic for the Users entity.
 */
@Service
public class UsersService {

    @Autowired
    protected UsersRepository usersRepository;
    
    @Autowired
    protected CredentialsRepository credentialsRepository;

    /**
     * This method retrieves a Users entity from the DB based on its ID.
     * 
     * @param id the id of the Users to retrieve
     * @return the retrieved Users entity, or null if not found
     */
    @Transactional
    public Users getUsers(Long id) {
        Optional<Users> result = this.usersRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * This method saves a Users entity in the DB.
     * 
     * @param users the Users entity to save
     * @return the saved Users entity
     * @throws DataIntegrityViolationException if a Users with the same CF already exists
     */
    @Transactional
    public Users saveUsers(Users users) {
        return this.usersRepository.save(users);
    }

    /**
     * This method retrieves all Users entities from the DB.
     * 
     * @return a List containing all Users entities
     */
    @Transactional
    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String username = null;

        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails userDetails) {
            username = userDetails.getUsername();
        } else if (principal instanceof String) {
            username = (String) principal;
        }

        if (username != null) {
            Credentials credentials = credentialsRepository.findByUsername(username).orElse(null);
            if (credentials != null) {
                return credentials.getUser();
            }
        }

        return null;
    }

    @Transactional
    public Users getOrCreateUser(OAuth2User principal) {
        if (principal == null) {
            // Proviamo a recuperarlo manualmente dal contesto
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof OAuth2User)) {
                return null; // non autenticato o non è un OAuth2User
            }
            principal = (OAuth2User) authentication.getPrincipal();
        }

        String email = principal.getAttribute("email"); // GitHub potrebbe usare "login"
        String name = principal.getAttribute("name");

        if (email == null) {
            throw new IllegalArgumentException("Email non trovata nell'OAuth2User");
        }

        // Controlla se l'utente esiste già
        Optional<Credentials> existingCredentials = credentialsRepository.findByUsername(email);
        if (existingCredentials.isPresent()) {
            return existingCredentials.get().getUser();
        }

        // Se non esiste, crealo
        Users user = new Users();
        user.setName(name != null ? name : "Utente");
        user = usersRepository.save(user);

        Credentials credentials = new Credentials();
        credentials.setUsername(email);
        credentials.setRole(Roles.USER);
        credentials.setUser(user);
        credentialsRepository.save(credentials);

        return user;
    }



}
