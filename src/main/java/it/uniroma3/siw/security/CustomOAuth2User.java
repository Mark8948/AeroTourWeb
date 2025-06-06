package it.uniroma3.siw.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import it.uniroma3.siw.model.tables.Users;

public class CustomOAuth2User implements OAuth2User {

    private final Users user;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(Users user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public Users getUser() {
        return user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + user.getCredentials().getRole().name())
        );
    }
}
