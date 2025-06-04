package it.uniroma3.siw.model.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import it.uniroma3.siw.model.enums.Roles;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@Entity
public class Credentials {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@Column(name = "credentials_id")  //ho bisogno che questa parte si chiami così per debuggare nel db
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Users user;
	
	@Enumerated(EnumType.STRING) //serve a far apparire nel db l'enum come uno string e non numero.
	@Column(nullable=false)
	private Roles role; // = Roles.USER; OMETTO QUI E INSERISCO IL DEFAULT ROLE NEL COSTRUTTORE PER MIGLIORE SICUREZZA
	
	
	public Credentials() {
		this.role = Roles.USER;
	}
	
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}
