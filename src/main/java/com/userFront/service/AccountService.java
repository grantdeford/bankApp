package com.userFront.service;

import java.security.Principal;

import com.userFront.domain.CheckingAccount;
import com.userFront.domain.CheckingTransaction;
import com.userFront.domain.SavingsAccount;
import com.userFront.domain.SavingsTransaction;

public interface AccountService {
	CheckingAccount createCheckingAccount();
	SavingsAccount createSavingsAccount();
	void deposit(String accountType, double amount, Principal principal);
	void withdraw(String accountType, double amount, Principal principal);
	
   
}
