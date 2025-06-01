package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class VisitsBooking {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	private String guideName;
	private Integer visitDate; // day,month,year of the visit in format DDMMYYYY.
	
	 @Enumerated(EnumType.STRING)
	    private VisitsBooking status;
	 
	public String getGuideName() {
		return guideName;
	}
	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}
	public Integer getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Integer date) {
		this.visitDate = date;
	}
	
	
	
}
