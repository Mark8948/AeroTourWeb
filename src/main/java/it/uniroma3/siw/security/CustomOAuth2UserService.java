package it.uniroma3.siw.security;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.tables.Credentials;
import it.uniroma3.siw.model.tables.Users;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UsersRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oauth2User.getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // Esempio di estrazione dati da Google o GitHub (puoi estendere per altri provider)
        String email = null;
        String name = null;
        String surname = null;
        String picture = null;

        if (registrationId.equalsIgnoreCase("google")) {
            email = (String) attributes.get("email");
            name = (String) attributes.get("given_name");
            surname = (String) attributes.get("family_name");
            picture = (String) attributes.get("picture");
        } else if (registrationId.equalsIgnoreCase("github")) {
            email = (String) attributes.get("email");
            if (email == null) {
                // GitHub a volte non restituisce email, gestire fallback o richiesta email separata
                email = (String) attributes.get("login") + "@github.com"; // esempio fallback
            }
            name = (String) attributes.get("name");
            if (name == null) name = (String) attributes.get("login");
            surname = "";
            picture = (String) attributes.get("avatar_url");
        } else {
            // Gestisci altri provider o fallback
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            surname = "";
            picture = null;
        }

        if (email == null) {
            throw new RuntimeException("Email non trovata da OAuth2 provider");
        }

        // Cerca utente per email
        Optional<Users> userOptional = usersRepository.findByEmail(email);

        Users user;
        if (userOptional.isPresent()) {
            user = userOptional.get();

            // Aggiorna info utente (es. nome, immagine)
            user.setName(name != null ? name : user.getName());
            user.setSurname(surname != null ? surname : user.getSurname());
            user.setPictureUrl(picture != null ? picture : user.getPictureUrl());

        } else {
            // Nuovo utente -> crealo e salva
            user = new Users();
            user.setEmail(email);
            user.setName(name != null ? name : "Unknown");
            user.setSurname(surname != null ? surname : "");
            user.setPictureUrl(picture);

            // Creo anche le credenziali con ruolo USER senza password (OAuth)
            Credentials cred = new Credentials();
            cred.setEmail(email);
            cred.setUsername(email); // oppure qualche altra logica
            cred.setRole(it.uniroma3.siw.model.enums.Roles.USER);
            cred.setUser(user);
            user.setCredentials(cred);

            usersRepository.save(user);
        }

        return new CustomOAuth2User(user, attributes);
    }
}
