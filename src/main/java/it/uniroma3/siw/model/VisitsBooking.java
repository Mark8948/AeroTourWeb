package it.uniroma3.siw.model;

//import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
	
	@Column(name = "date_time_visit")
	private LocalDateTime visitDateTime; // day,month,year of the visit in format DDMMYYYY.
	
	@Column(length = 10)
	private Integer guideCellNumber;
	
	// @Enumerated(EnumType.STRING)
	//    private VisitsBooking status;
	
	//TODO: Sistemare questo macello
	
	
	public String getGuideName() {
		return guideName;
	}
	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}
	public LocalDateTime getVisitDate() {
		return visitDateTime;
	}
	public void setVisitDate(LocalDateTime date) {
		this.visitDateTime = date;
	}
	public Integer getGuideCellNumber() {
		return guideCellNumber;
	}
	public void setGuideCellNumber(Integer guideCellNumber) {
		this.guideCellNumber = guideCellNumber;
	}
	
	
	
}
