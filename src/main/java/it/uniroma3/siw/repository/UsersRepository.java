package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.tables.Users;

public interface UsersRepository extends CrudRepository<Users, Long>{
	Optional<Users> findByEmail(String email);



}
