package it.uniroma3.siw.model.tables;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AirplaneCustomization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String descriptionName;
	
	@Column(nullable = false, length = 2000)
	private String description;
	
	private LocalDate modificationFirstAvailabilityDate; //date since when a modification is available to purchase in format DDMMYYYY.
	private float modificationPrice;
	private String urlImage;
	
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
		return descriptionName;
	}
	public void setDescriptionName(String descriptionName) {
		this.descriptionName = descriptionName;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
}
