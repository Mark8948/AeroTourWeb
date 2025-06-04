package it.uniroma3.siw.model.tables;

import java.time.LocalDate;
import java.util.List;

//import it.uniroma3.siw.model.enums.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;



@Entity
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable=false)
	private String surname;
	
	@Column(nullable=false, length = 16, unique = true)
	private String CF;  //CODICE FISCALE
	
	private LocalDate dateOfBirth;
	
	@OneToMany(mappedBy = "user")
	private List<VisitsBooking> bookings;
	
	public List<VisitsBooking> getBookings() {
		return bookings;
	}
	public void setBookings(List<VisitsBooking> bookings) {
		this.bookings = bookings;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getCF() {
		return CF;
	}
	public void setCF(String cF) {
		CF = cF;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Users other = (Users) obj;
	    return id != null && id.equals(other.id);
	}

	@Override
	public int hashCode() {
	    return 31;
	}
	
}
