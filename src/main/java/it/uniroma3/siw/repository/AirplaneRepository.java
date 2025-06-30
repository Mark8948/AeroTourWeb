package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.tables.Airplane;

public interface AirplaneRepository extends CrudRepository<Airplane, Long> {
	//metodi personalizzati
	
	//@Query("SELECT a FROM Airplane a LEFT JOIN FETCH a.customizations WHERE a.id = :id")
	//Optional<Airplane> findWithCustomizations(@Param("id") Long id);
}
