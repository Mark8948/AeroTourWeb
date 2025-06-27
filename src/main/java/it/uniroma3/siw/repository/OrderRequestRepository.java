package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.tables.OrderRequest;
import it.uniroma3.siw.model.tables.Users;

public interface OrderRequestRepository extends JpaRepository<OrderRequest, Long>{
	 List<OrderRequest> findOrdersByUser(Users user);
}
