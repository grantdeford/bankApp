package com.userFront.Dao;

import org.springframework.data.repository.CrudRepository;

import com.userFront.domain.Customer;

public interface UserDao extends CrudRepository<Customer, Long>{
	Customer findByUsername(String username);
	Customer findByEmail(String email);
}
