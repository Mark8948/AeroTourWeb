package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.tables.OrderRequest;

public interface OrderRequestRepository extends CrudRepository<OrderRequest, Long>{
	//metodi personalizzati
}
