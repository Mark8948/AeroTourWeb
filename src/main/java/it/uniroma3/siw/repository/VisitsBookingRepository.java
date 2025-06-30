package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import it.uniroma3.siw.model.tables.Users;
import it.uniroma3.siw.model.tables.VisitsBooking;

public interface VisitsBookingRepository extends JpaRepository<VisitsBooking, Long>{
	//metodi personalizzati
	List<VisitsBooking> findByUser(Users user);
	@NonNull
	List<VisitsBooking> findAll();
}
