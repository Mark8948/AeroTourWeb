package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.tables.Airplane;
import jakarta.transaction.Transactional;

public interface AirplaneRepository extends JpaRepository<Airplane, Long> {

    // 1) Scollega tutti gli OrderRequest (come prima)
    @Transactional
    @Modifying
    @Query("UPDATE OrderRequest o SET o.airplane = NULL WHERE o.airplane.id = :airplaneId")
    int detachOrdersByAirplaneId(@Param("airplaneId") Long airplaneId);

    // 2) Elimina tutte le AirplaneCustomization collegate
    @Transactional
    @Modifying
    @Query("DELETE FROM AirplaneCustomization c WHERE c.airplane.id = :airplaneId")
    int deleteCustomizationsByAirplaneId(@Param("airplaneId") Long airplaneId);

    // 3) Infine elimina l’Airplane
    @Transactional
    @Modifying
    @Query("DELETE FROM Airplane a WHERE a.id = :airplaneId")
    int deleteAirplaneById(@Param("airplaneId") Long airplaneId);

}

