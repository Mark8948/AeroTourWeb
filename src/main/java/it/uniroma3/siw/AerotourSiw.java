package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class AerotourSiw {

    public static void main(String[] args) {
        SpringApplication.run(AerotourSiw.class, args);
    }

}
