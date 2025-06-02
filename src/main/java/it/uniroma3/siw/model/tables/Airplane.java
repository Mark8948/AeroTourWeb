package it.uniroma3.siw.model.tables;

//import java.time.LocalDate;
import java.time.Year;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import java.util.Objects;

@Entity
public class Airplane {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String modelName;
    
    @Column(length = 2000, nullable = false)
    private String description;
    
    private Year buildYear;
    
    private String urlImage;
    
    private float price;
    
    // ====================
    // equals() / hashCode()
    // ====================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
    public Year getBuildYear() {
        return buildYear;
    }
    public void setBuildYear(Year buildYear) {
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
