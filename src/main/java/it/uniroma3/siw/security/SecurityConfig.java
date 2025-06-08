package it.uniroma3.siw.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        //authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @SuppressWarnings("removal")
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	return http
        	//.csrf(Customizer.withDefaults())
    		.csrf().disable()
        	.authorizeHttpRequests(authorize -> {
        		authorize.requestMatchers("/", "/contatti", "/homepage","/register", "/login", "/css/**", "/js/**", "/images/**").permitAll();
        		authorize.anyRequest().authenticated();
        	})
        	.formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                    .loginPage("/login")
                    .userInfoEndpoint(userInfo -> userInfo
                        .userService(customOAuth2UserService)
                    )
                    .successHandler((requeste, response, autentication)->response.sendRedirect("/"))
                )
                .authenticationProvider(authenticationProvider())
        	.build();
        	
        /*
            .csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/contatti", "/homepage","/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService)
                )
                .defaultSuccessUrl("/", true)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .authenticationProvider(authenticationProvider());
        

        return http.build();
        */
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
