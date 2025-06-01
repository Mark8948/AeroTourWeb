package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AirplaneCustomization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String description;
	private int modificationFirstAvailabilityDate; //date from when a modification is available to purchase in format DDMMYYYY.
	private int modificationPrice;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getModificationFirstAvailabilityDate() {
		return modificationFirstAvailabilityDate;
	}
	public void setModificationFirstAvailabilityDate(int modificationAvailabilityDate) {
		this.modificationFirstAvailabilityDate = modificationAvailabilityDate;
	}
	public int getModificationPrice() {
		return modificationPrice;
	}
	public void setModificationPrice(int modificationPrice) {
		this.modificationPrice = modificationPrice;
	}
	
}
