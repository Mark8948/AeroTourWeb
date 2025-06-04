package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
//import java.util.List;
import java.util.Optional;

import it.uniroma3.siw.model.tables.VisitsBooking;

public interface VisitsBookingRepository extends CrudRepository<VisitsBooking, Long>{
	//metodi personalizzati
}
