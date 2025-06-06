package it.uniroma3.siw.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.tables.Credentials;
import it.uniroma3.siw.model.tables.Users;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UsersRepository;

@Controller
public class RegistrationController {

	@Autowired 
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private UsersRepository userRepository;

    /**
     * GET /register: mostra il form di registrazione.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("credForm", new Credentials());
        model.addAttribute("userForm", new Users());
        return "register";
    }

    /**
     * POST /register: elabora i dati del form, verifica unicità e salva.
     */
    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("userForm") Users userForm,
            @ModelAttribute("credForm") Credentials credForm,
            Model model
    ) {
        // 1) Controllo che username non sia già in uso
        Optional<Credentials> byUsername = credentialsRepository.findByUsername(credForm.getUsername());
        if (byUsername.isPresent()) {
            model.addAttribute("usernameError", "Username già in uso");
            return "register";
        }

        // 2) Controllo che email non sia già in uso nelle credenziali
        Optional<Credentials> byEmailCred = credentialsRepository.findByEmail(credForm.getEmail());
        if (byEmailCred.isPresent()) {
            model.addAttribute("emailError", "Esiste già un account con questa email");
            return "register";
        }

        // 3) Controllo che email non sia già in uso in Users (es. registrato via OAuth2)
        Optional<Users> byEmailUser = userRepository.findByEmail(userForm.getEmail());
        if (byEmailUser.isPresent()) {
            model.addAttribute("emailError", "Esiste già un utente (OAuth2 o form) con questa email");
            return "register";
        }

        // 4) Creo l’oggetto Users e lo popolo
        Users user = new Users();
        user.setName(userForm.getName());
        user.setSurname(userForm.getSurname());
        user.setEmail(userForm.getEmail());
        user.setPictureUrl(null);

        // 5) Creo l’oggetto Credentials, collegandolo a Users
        Credentials cred = new Credentials();
        cred.setUsername(credForm.getUsername());
        cred.setEmail(credForm.getEmail());
        cred.setPassword(passwordEncoder.encode(credForm.getPassword()));
        cred.setUser(user);
        user.setCredentials(cred);

        // 6) Salvo tramite credentialsRepository: grazie a CascadeType.ALL,
        //    verrà creata anche la riga in Users
        credentialsRepository.save(cred);

        return "redirect:/login?registered=true";
    }

    
}
