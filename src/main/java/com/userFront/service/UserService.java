package com.userFront.service;

import java.util.Set;

import com.userFront.domain.Customer;
import com.userFront.domain.security.UserRole;

public interface UserService { 
	Customer findByUsername(String username);
	
	Customer findByEmail(String email);
	
	boolean checkUserExists(String username, String email);
	
	boolean checkUsernameExists(String username);
	
	boolean checkEmailExists(String email);
	
	void save (Customer customer);
	
	Customer createUser(Customer customer, Set<UserRole> userRoles);
	
}
