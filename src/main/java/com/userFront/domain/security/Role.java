package com.userFront.domain.security;
// define a role class as an Entity that will be persisted in our database
// roles 2 Admin and user

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.Id;

@Entity
public class Role {
	@Id
	private int roleId;
	
	private String name;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<UserRole> userRoles = new HashSet<>();
	
	public Role() {
		
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
}
