package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.tables.Users;

public interface UsersRepository extends CrudRepository<Users, Long>{
	/**
     * Cerca un utente in base al codice fiscale (campo CF), che è unico.
     * @param cf il codice fiscale da ricercare
     * @return un Optional contenente l’Users corrispondente, o vuoto se non esiste
     */
    Optional<Users> findByCF(String cf);

}
