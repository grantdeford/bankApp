package com.userFront.Dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.userFront.domain.CheckingTransaction;

public interface CheckingTransactionDao extends CrudRepository<CheckingTransaction, Long> {
	
	List<CheckingTransaction> findAll();
}
