package com.userFront.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userFront.Dao.CheckingAccountDao;
import com.userFront.Dao.CheckingTransactionDao;
import com.userFront.Dao.SavingsAccountDao;
import com.userFront.Dao.SavingsTransactionDao;
import com.userFront.domain.CheckingAccount;
import com.userFront.domain.CheckingTransaction;
import com.userFront.domain.Customer;
import com.userFront.domain.SavingsAccount;
import com.userFront.domain.SavingsTransaction;
import com.userFront.service.TransactionService;
import com.userFront.service.UserService;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CheckingTransactionDao checkingTransactionDao;
	
	@Autowired
	private SavingsTransactionDao savingsTransactionDao;
	
	@Autowired
	private CheckingAccountDao checkingAccountDao;
	
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	
	@Autowired
	private RecipientDao recipientDao;
	

	public List<CheckingTransaction> findCheckingTransactionList(String username){
        Customer customer = userService.findByUsername(username);
        List<CheckingTransaction> checkingTransactionList = customer.getCheckingAccount().getCheckingTransactionList();

        return checkingTransactionList;
    }

    public List<SavingsTransaction> findSavingsTransactionList(String username) {
        Customer customer = userService.findByUsername(username);
        List<SavingsTransaction> savingsTransactionList = customer.getSavingsAccount().getSavingsTransactionList();

        return savingsTransactionList;
    }

    public void saveCheckingDepositTransaction(CheckingTransaction checkingTransaction) {
        checkingTransactionDao.save(checkingTransaction);
    }

    public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionDao.save(savingsTransaction);
    }
    
    public void saveCheckingWithdrawTransaction(CheckingTransaction checkingTransaction) {
        checkingTransactionDao.save(checkingTransaction);
    }

    public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionDao.save(savingsTransaction);
    }
    
    public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, CheckingAccount checkingAccount, SavingsAccount savingsAccount) throws Exception {
        if (transferFrom.equalsIgnoreCase("Checking") && transferTo.equalsIgnoreCase("Savings")) {
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            checkingAccountDao.save(checkingAccount);
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();

            CheckingTransaction checkingTransaction = new CheckingTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Account", "Finished", Double.parseDouble(amount), checkingAccount.getAccountBalance(), checkingAccount);
            checkingTransactionDao.save(checkingTransaction);
        } else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Checking")) {
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            checkingAccountDao.save(checkingAccount);
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
            savingsTransactionDao.save(savingsTransaction);
        } else {
            throw new Exception("Invalid Transfer");
        }
    }
    
    public List<Recipient> findRecipientList(Principal principal) {
        String username = principal.getName();
        List<Recipient> recipientList = recipientDao.findAll().stream() 			//convert list to stream
                .filter(recipient -> username.equals(recipient.getUser().getUsername()))	//filters the line, equals to username
                .collect(Collectors.toList());

        return recipientList;
    }

    public Recipient saveRecipient(Recipient recipient) {
        return recipientDao.save(recipient);
    }

    public Recipient findRecipientByName(String recipientName) {
        return recipientDao.findByName(recipientName);
    }

    public void deleteRecipientByName(String recipientName) {
        recipientDao.deleteByName(recipientName);
    }
    
    public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, CheckingAccount checkingAccount, SavingsAccount savingsAccount) {
        if (accountType.equalsIgnoreCase("Checking")) {
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            checkingAccountDao.save(checkingAccount);

            Date date = new Date();

            CheckingTransaction checkingTransaction = new CheckingTransaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), checkingAccount.getAccountBalance(), checkingAccount);
            checkingTransactionDao.save(checkingTransaction);
        } else if (accountType.equalsIgnoreCase("Savings")) {
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);

            Date date = new Date();

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer to recipient "+recipient.getName(), "Transfer", "Finished", Double.parseDouble(amount), savingsAccount.getAccountBalance(), savingsAccount);
            savingsTransactionDao.save(savingsTransaction);
        }
    }
}
