package com.userFront.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userFront.domain.CheckingAccount;
import com.userFront.domain.CheckingTransaction;
import com.userFront.domain.Customer;
import com.userFront.domain.SavingsAccount;
import com.userFront.domain.SavingsTransaction;
import com.userFront.service.AccountService;
import com.userFront.service.TransactionService;
import com.userFront.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping("/checkingAccount")
	public String checkingAccount(Model model, Principal principal ) {
		List<CheckingTransaction>checkingTransactionList = transactionService.findCheckingTransactionList(principal.getName());
		
		Customer customer = userService.findByUsername(principal.getName());
		CheckingAccount checkingAccount = customer.getCheckingAccount();
		
		model.addAttribute("checkingAccount", checkingAccount);
		
		return "checkingAccount";
	}
	
	@RequestMapping("/savingsAccount")
	public String savingsAccount(Model model, Principal principal) {
		List<SavingsTransaction>savingsTransactionList = transactionService.findSavingsTransactionList(principal.getName());

		
		Customer customer = userService.findByUsername(principal.getName());
		SavingsAccount savingsAccount = customer.getSavingsAccount();
		
		model.addAttribute("savingsAccount", savingsAccount);
		
		return "savingsAccount";
	}
	
	@RequestMapping(value = "/deposit", method =RequestMethod.GET)
	public String deposit(Model model) {
		model.addAttribute("accountType", "");
		model.addAttribute("amount", "");
		
		return "deposit";
	}
	
	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	public String depositPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal) {
		accountService.deposit(accountType, Double.parseDouble(amount), principal);
		
		return "redirect:/userFront";
	}
}
