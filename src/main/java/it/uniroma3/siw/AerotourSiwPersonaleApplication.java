package it.uniroma3.siw;

//import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.uniroma3.siw.model.enums.Roles;
import it.uniroma3.siw.model.tables.Credentials;
import it.uniroma3.siw.model.tables.Users;
import it.uniroma3.siw.repository.CredentialsRepository;
//import it.uniroma3.siw.repository.UsersRepository;

@SpringBootApplication
public class AerotourSiwPersonaleApplication {
	
	//@Autowired
	//private CredentialsRepository credRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(AerotourSiwPersonaleApplication.class, args);
	}
	
	/*@Override
	public void run(String... args) throws Exception{
		Users users = new Users();
		Credentials credential = new Credentials();
		
		
		users.setName("Marco");
		users.setSurname("Altamura");
		
		credential.setRole(Roles.SERVER_ADMINISTRATOR);
		credential.setPassword("admin");
		credential.setEmail("mar.altamura2@stud.uniroma3.it");
		credential.setUser(users);
		credential.setUsername("admin");
		
		
		//usersRepo.save(users);
		credRepo.save(credential);
	}*/

}
