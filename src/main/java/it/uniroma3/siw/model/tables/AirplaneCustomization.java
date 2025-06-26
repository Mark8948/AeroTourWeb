package it.uniroma3.siw.model.tables;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class AirplaneCustomization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String modificationName;
	
	@Column(nullable = false, length = 2000)
	private String description;
	
	private LocalDate modificationFirstAvailabilityDate;

	private float modificationPrice;

	@ManyToOne
	@JoinColumn(name = "airplane_id")  // nome della colonna FK nella tabella AirplaneCustomization
	private Airplane airplane;

	// =================
	// Getters & Setters
	// =================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModificationName() {
		return modificationName;
	}

	public void setModificationName(String modificationName) {
		this.modificationName = modificationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getModificationFirstAvailabilityDate() {
		return modificationFirstAvailabilityDate;
	}

	public void setModificationFirstAvailabilityDate(LocalDate modificationAvailabilityDate) {
		this.modificationFirstAvailabilityDate = modificationAvailabilityDate;
	}

	public float getModificationPrice() {
		return modificationPrice;
	}

	public void setModificationPrice(float modificationPrice) {
		this.modificationPrice = modificationPrice;
	}

	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}
}
