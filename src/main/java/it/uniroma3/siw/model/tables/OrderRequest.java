package it.uniroma3.siw.model.tables;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.uniroma3.siw.model.enums.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

@Entity
public class OrderRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false)
	private Users user;

	@Column(nullable = false)
	private LocalDateTime creationDate;

	@Enumerated(EnumType.STRING) // string in db invece che un numero
	@Column(nullable = false, length = 25)
	private Status stato;
	
	@Column(nullable = false)
	private float totalPrice;


	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Status getStato() {
		return stato;
	}

	public void setStato(Status stato) {
		this.stato = stato;
	}

	// Riferimento al singolo aereo ordinato
	@ManyToOne(optional = false)
	private Airplane airplane;

	// Lista delle modifiche applicate a questo aereo nell'ordine
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "list_modifications_applied", joinColumns = @JoinColumn(name = "orderrequest_id"), inverseJoinColumns = @JoinColumn(name = "customization_id"))
	private List<AirplaneCustomization> customizations = new ArrayList<>();

	// equals e hashCode come prima
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof OrderRequest))
			return false;
		OrderRequest other = (OrderRequest) o;
		return id != null && id.equals(other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	// getters e setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Airplane getAirplane() {
		return airplane;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	public List<AirplaneCustomization> getCustomizations() {
		return customizations;
	}

	public void setCustomizations(List<AirplaneCustomization> customizations) {
		this.customizations = customizations;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
}
