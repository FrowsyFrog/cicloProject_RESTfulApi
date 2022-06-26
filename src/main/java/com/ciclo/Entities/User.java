package com.ciclo.Entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "_user",
		uniqueConstraints = {
			@UniqueConstraint(columnNames = "nombreUsuario"),
			@UniqueConstraint(columnNames = "email")
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idUser;
	@Column(name = "nombreUsuario")
	private String username;
	@Column(name = "email")
	private String email;
	@Column(name = "imagenUsuario")
	private String imageurl;
	@Column(name = "contrasenha")
	private String password;
	@Column(name = "metodoEncriptacion")
	private String cryptmethod;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable( name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	//private List<Report> reports;

}
