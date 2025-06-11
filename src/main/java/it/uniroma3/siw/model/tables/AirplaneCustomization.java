package it.uniroma3.siw.model.tables;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AirplaneCustomization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String modificationName;
	
	@Column(nullable = false, length = 2000)
	private String description;
	
	private LocalDate modificationFirstAvailabilityDate;
	private float modificationPrice;
	private String urlImage;
	
	
	@ManyToOne
	@JoinColumn(name = "airplane_id")  // nome della colonna FK nella tabella AirplaneCustomization
	private Airplane airplane;
	
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescriptionName() {
		return modificationName;
	}
	public void setDescriptionName(String modificationName) {
		this.modificationName= modificationName;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
}
