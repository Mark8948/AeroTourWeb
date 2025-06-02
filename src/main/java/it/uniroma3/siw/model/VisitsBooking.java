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
	private LocalDateTime visitDateTime;
	
	@Column(length = 10)
	private Integer guideCellNumber;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.IN_ATTESA_DI_CONFERMA;

	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public LocalDateTime getVisitDateTime() {
		return visitDateTime;
	}
	public void setVisitDateTime(LocalDateTime visitDateTime) {
		this.visitDateTime = visitDateTime;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
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
