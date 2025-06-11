package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.tables.Users;
import it.uniroma3.siw.model.tables.VisitsBooking;

public interface VisitsBookingRepository extends CrudRepository<VisitsBooking, Long>{
	//metodi personalizzati
	List<VisitsBooking> findByUser(Users user);
}
