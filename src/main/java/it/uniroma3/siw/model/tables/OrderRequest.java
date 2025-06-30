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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class OrderRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Status stato;

    @Column(nullable = false)
    private float totalPrice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;

    @OneToMany(
        mappedBy = "orderRequest",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<AirplaneCustomization> customizations = new ArrayList<>();

    // =================
    // Getters & Setters
    // =================

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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
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
        for (AirplaneCustomization c : customizations) {
            c.setOrderRequest(this);
        }
    }

    /**
     * Aggiunge una personalizzazione e imposta la FK lato figlio
     */
    public void addCustomization(AirplaneCustomization c) {
        customizations.add(c);
        c.setOrderRequest(this);
    }

    /**
     * Rimuove una personalizzazione e scollega la FK lato figlio
     */
    public void removeCustomization(AirplaneCustomization c) {
        customizations.remove(c);
        c.setOrderRequest(null);
    }

    // ================
    // equals e hashCode
    // ================

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
}
