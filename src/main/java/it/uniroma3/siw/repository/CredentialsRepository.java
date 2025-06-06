package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.tables.Credentials;


public interface CredentialsRepository extends CrudRepository<Credentials, Long>{
	Optional<Credentials> findByUsername(String username);
	Optional<Credentials> findByEmail(String email);
	
}
