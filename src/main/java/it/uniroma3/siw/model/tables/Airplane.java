package it.uniroma3.siw.model.tables;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.*;

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
	
	@Column
	private Integer buildYear;

	@Lob
	@Column(nullable = true)
	private byte[] image;
	
	 @Transient
	 private MultipartFile imageFile; //Transiente per upload di immagini

	private float price;

	@OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AirplaneCustomization> customizations = new ArrayList<>();

	@OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VisitsBooking> visitsBookings = new ArrayList<>();

	// ================
	// Getters / Setters
	// ================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<AirplaneCustomization> getCustomizations() {
		return customizations;
	}

	public void setCustomizations(List<AirplaneCustomization> customizations) {
		this.customizations = customizations;
	}

	public List<VisitsBooking> getVisitsBookings() {
		return visitsBookings;
	}

	public void setVisitsBookings(List<VisitsBooking> visitsBookings) {
		this.visitsBookings = visitsBookings;
	}

	// ================
	// equals / hashCode
	// ================

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
}
