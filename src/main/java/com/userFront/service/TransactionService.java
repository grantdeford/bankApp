package com.userFront.service;

import java.util.List;

import com.userFront.domain.CheckingTransaction;
import com.userFront.domain.SavingsTransaction;

public interface TransactionService {
	
	List<CheckingTransaction>findCheckingTransactionList(String username);
	
	List<SavingsTransaction>findSavingsTransactionList(String username);
	
	void saveCheckingDepositTransaction(CheckingTransaction checkingTransaction);
	void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);
	
	void saveCheckingWithdrawTransaction(CheckingTransaction checkingTransaction);
	void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);
	
}
