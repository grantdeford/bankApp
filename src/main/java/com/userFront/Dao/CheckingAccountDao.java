package com.userFront.Dao;

import org.springframework.data.repository.CrudRepository;

import com.userFront.domain.CheckingAccount;

public interface CheckingAccountDao extends CrudRepository<CheckingAccount, Long> {
	
	CheckingAccount findByAccountNumber (int accountNumber);
}
