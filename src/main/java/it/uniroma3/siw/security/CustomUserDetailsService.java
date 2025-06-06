package it.uniroma3.siw.security;

import it.uniroma3.siw.model.tables.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credentials cred = credentialsRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

        return User.builder()
            .username(cred.getUsername())
            .password(cred.getPassword()) // deve essere già codificata (bcrypt)
            .roles(cred.getRole().name()) // oppure usa cred.getRole() se hai i ruoli nel DB
            .build();
    }
}
