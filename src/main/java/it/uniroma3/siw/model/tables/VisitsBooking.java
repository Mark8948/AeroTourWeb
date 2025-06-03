package it.uniroma3.siw.model.tables;

//import java.time.LocalDate;
import java.time.LocalDateTime;

import it.uniroma3.siw.model.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToOne;

@Entity
public class VisitsBooking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String guideName;

	@Column(name = "visit_date_time", nullable = false)
	private LocalDateTime visitDateTime;

	@Column(length = 10)
	private Integer guideCellNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.IN_ATTESA_DI_CONFERMA;

	@ManyToOne
	private Users user;

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getGuideCellNumber() {
		return guideCellNumber;
	}

	public void setGuideCellNumber(Integer guideCellNumber) {
		this.guideCellNumber = guideCellNumber;
	}
}
