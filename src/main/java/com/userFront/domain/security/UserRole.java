package com.userFront.domain.security;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.userFront.domain.Customer;

@Entity
@Table(name="user_role")
public class UserRole {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long userRoleId;
		
		public UserRole(Customer customer, Role role) {
			this.customer = customer;
			this.role = role;
		}
		
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "user_id")
		private Customer customer;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "role_id")
		private Role role;
		
		public UserRole() {
			
		}

		public long getUserRoleId() {
			return userRoleId;
		}

		public void setUserRoleId(long userRoleId) {
			this.userRoleId = userRoleId;
		}

		public Customer getCustomer() {
			return customer;
		}

		public void setCustomer(Customer customer) {
			this.customer = customer;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}
		
		
}
