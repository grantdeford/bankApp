package com.userFront.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userFront.Dao.CheckingAccountDao;
import com.userFront.Dao.SavingsAccountDao;
import com.userFront.domain.CheckingAccount;
import com.userFront.domain.CheckingTransaction;
import com.userFront.domain.Customer;
import com.userFront.domain.SavingsAccount;
import com.userFront.domain.SavingsTransaction;
import com.userFront.service.AccountService;
import com.userFront.service.TransactionService;
import com.userFront.service.UserService;

@Service
public class AccountServiceImpl implements AccountService {
	
	private static int nextAccountNumber = 11223145;
	
	@Autowired
	private CheckingAccountDao checkingAccountDao;
	
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;
	
	public CheckingAccount createCheckingAccount() {
		CheckingAccount checkingAccount = new CheckingAccount();
		checkingAccount.setAccountBalance(new BigDecimal(0.0));
		checkingAccount.setAccountNumber(accountGen());
		
		checkingAccountDao.save(checkingAccount);
		
		return checkingAccountDao.findByAccountNumber(checkingAccount.getAccountNumber());
	}
	public SavingsAccount createSavingsAccount() {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(new BigDecimal(0.0));
		savingsAccount.setAccountNumber(accountGen());
		
		savingsAccountDao.save(savingsAccount);
		
		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
	}
	
	public void deposit(String accountType, double amount, Principal principal) {
        Customer customer = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Checking")) {
            CheckingAccount checkingAccount = customer.getCheckingAccount();
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().add(new BigDecimal(amount)));
            checkingAccountDao.save(checkingAccount);

            Date date = new Date();

            CheckingTransaction checkingTransaction = new CheckingTransaction(date, "Deposit to Checking Account", "Account", "Finished", amount, checkingAccount.getAccountBalance(), checkingAccount);
            transactionService.saveCheckingDepositTransaction(checkingTransaction);
            
        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = customer.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
        }
    }
    
    public void withdraw(String accountType, double amount, Principal principal) {
        Customer customer = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Checking")) {
            CheckingAccount checkingAccount = customer.getCheckingAccount();
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            checkingAccountDao.save(checkingAccount);

            Date date = new Date();

            CheckingTransaction checkingTransaction = new CheckingTransaction(date, "Withdraw from Checking Account", "Account", "Finished", amount, checkingAccount.getAccountBalance(), checkingAccount);
            transactionService.saveCheckingWithdrawTransaction(checkingTransaction);
        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = customer.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
        }
    }
	
	private int accountGen() {
		return ++nextAccountNumber;
	}
	
}
