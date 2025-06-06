package it.uniroma3.siw.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.uniroma3.siw.model.tables.Credentials;
import it.uniroma3.siw.model.tables.Users;

public class CustomUserDetails implements UserDetails {

    private final Credentials credentials;
    private final Users user;

    public CustomUserDetails(Users user) {
        this.user = user;
        this.credentials = user.getCredentials();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (credentials != null && credentials.getRole() != null) {
            // Ruolo enum -> GrantedAuthority con prefisso "ROLE_"
            return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + credentials.getRole().name())
            );
        }
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return (credentials != null) ? credentials.getPassword() : null;
    }

    @Override
    public String getUsername() {
        return (credentials != null) ? credentials.getUsername() : user.getEmail();
        // fallback all'email se username assente (utile se OAuth2)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // puoi personalizzare
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // puoi personalizzare
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // puoi personalizzare
    }

    @Override
    public boolean isEnabled() {
        return true; // puoi personalizzare
    }

    // Per eventuali usi esterni
    public Users getUser() {
        return user;
    }
}
