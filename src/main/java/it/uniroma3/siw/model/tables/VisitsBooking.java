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

	@Column(name = "visit_date_time")
	private LocalDateTime visitDateTime;

	@Column(name = "guide_surname", length = 100)
	private String guideSurname;

	@Column(name = "guide_phone", length = 20)
	private String guidePhone;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.IN_ATTESA_DI_CONFERMA;

	@ManyToOne
	private Users user;

	@ManyToOne(optional = false)
	private Airplane airplane;

	// getter / setter
	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

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

	public String getGuideSurname() {
		return guideSurname;
	}

	public void setGuideSurname(String s) {
		this.guideSurname = s;
	}

	public String getGuidePhone() {
		return guidePhone;
	}

	public void setGuidePhone(String p) {
		this.guidePhone = p;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getVisitDateTime() {
		return visitDateTime;
	}

	public void setVisitDateTime(LocalDateTime visitDateTime) {
		this.visitDateTime = visitDateTime;
	}

}
