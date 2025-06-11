package it.uniroma3.siw.model.tables;

//import java.time.LocalDate;
//import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
//import java.util.Objects;

@Entity
@Table(name = "airplanes")
public class Airplane {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String modelName;

	@Column(length = 2000, nullable = false)
	private String description;

	private Integer buildYear;

	@Column(name = "url_image")
	private String urlImage;

	private float price;

	@OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AirplaneCustomization> customizations = new ArrayList<>();

	@OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VisitsBooking> visitsBookings = new ArrayList<>();

	public List<VisitsBooking> getVisitsBookings() {
		return visitsBookings;
	}

	public void setVisitsBookings(List<VisitsBooking> visitsBookings) {
		this.visitsBookings = visitsBookings;
	}

	// ====================
	// equals() / hashCode()
	// ====================
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Airplane airplane = (Airplane) o;
		return id != null && id.equals(airplane.id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// ================
	// getters / setters
	// ================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<AirplaneCustomization> getCustomizations() {
		return customizations;
	}

	public void setCustomizations(List<AirplaneCustomization> customizations) {
		this.customizations = customizations;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(Integer buildYear) {
		this.buildYear = buildYear;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
