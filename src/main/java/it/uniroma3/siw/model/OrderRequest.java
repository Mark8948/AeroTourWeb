package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OrderRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //TODO: Sistemare tutto il resto

    //private String engineType;
    //private String interiorOption;
    //private String avionicsPackage;

    //private Double totalPrice;

    //BOZZA NON PRENDERE SUL SERIO QUESTE COSE

    // Getters e setters
}
