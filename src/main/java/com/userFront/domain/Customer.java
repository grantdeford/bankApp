package com.userFront.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userFront.domain.security.Authority;
import com.userFront.domain.security.UserRole;

@Entity
public class Customer implements UserDetails{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId", nullable = false, updatable = false)
	private Long userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	private String phone;
	
	private boolean enabled=true;
	
	@OneToOne
	private CheckingAccount checkingAccount;
	
	@OneToOne
	private SavingsAccount savingsAccount;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JsonIgnore
	private List<Appointment> appointmentList;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private List<Recipient> recipientList;
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<>();
	
	public Set<UserRole> getUserRoles(){
		return userRoles;
	}
	
	public void setUserRoles(Set<UserRole>userRoles) {
		this.userRoles = userRoles;
	}
	
	
	
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
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


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public CheckingAccount getCheckingAccount() {
		return checkingAccount;
	}


	public void setCheckingAccount(CheckingAccount checkingAccount) {
		this.checkingAccount = checkingAccount;
	}


	public SavingsAccount getSavingsAccount() {
		return savingsAccount;
	}


	public void setSavingsAccount(SavingsAccount savingsAccount) {
		this.savingsAccount = savingsAccount;
	}


	public List<Appointment> getAppointmentList() {
		return appointmentList;
	}


	public void setAppointmentList(List<Appointment> appointmentList) {
		this.appointmentList = appointmentList;
	}


	public List<Recipient> getRecipientList() {
		return recipientList;
	}


	public void setRecipientList(List<Recipient> recipientList) {
		this.recipientList = recipientList;
	}


	@Override
	public String toString() {
		return "Customer [userId=" + userId + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + ", enabled="
				+ enabled + ", checkingAccount=" + checkingAccount + ", savingsaccount=" + savingsAccount
				+ ", appointmentList=" + appointmentList + ", recipientList=" + recipientList + "]";
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		userRoles.forEach(ur -> authorities.add(new Authority(ur.getRole().getName())));
		return authorities;
		
		
	}


	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	


	
}