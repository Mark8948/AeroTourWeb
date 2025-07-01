package it.uniroma3.siw.model.tables;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;      // dato "given_name" da Google o compilato a mano

    @Column(nullable = false)
    private String surname;   // dato "family_name" da Google o compilato a mano

    @Column(nullable = false, unique = true)
    private String email;     // chiave univoca per identificare l'utente

    @Column
    private String pictureUrl;   // TODO remove

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Credentials credentials;

    public Users() { }

    public Users(String name, String surname, String email, String pictureUrl) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pictureUrl = pictureUrl;
    }

    // --- Getter / Setter ---
    public Long getId() {
        return id;
    }
    // Non esponiamo setId(Long) poiché JPA lo gestisce

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
        // Manteniamo coerenza bidirezionale: se "credentials.user" non punta già a questo Users, lo aggiorniamo
        if (credentials != null && credentials.getUser() != this) {
            credentials.setUser(this);
        }
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
