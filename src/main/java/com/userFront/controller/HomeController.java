package com.userFront.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userFront.Dao.RoleDao;
import com.userFront.domain.CheckingAccount;
import com.userFront.domain.Customer;
import com.userFront.domain.SavingsAccount;
import com.userFront.domain.security.UserRole;
import com.userFront.service.UserService;

@Controller
public class HomeController {
	
		@Autowired
		private UserService userService;
		
		@Autowired 
		private RoleDao roleDao;
		
		@RequestMapping("/")
		public String home() {
			return "redirect:/index";
		}
		
		@RequestMapping("/index")
		public String index() {
			return "index";
		}
		
		@RequestMapping(value = "/signup", method = RequestMethod.GET)
		public String signup(Model model) {
			Customer customer = new Customer();
			
			model.addAttribute("customer", customer);
			return "signup";
		}
		
		@RequestMapping(value = "/signup", method = RequestMethod.POST)
		public String signupPost(@ModelAttribute("customer") Customer customer, Model model) {
			
			if(userService.checkUserExists(customer.getUsername(),customer.getEmail())) {
				
				if (userService.checkEmailExists(customer.getEmail())) {
					model.addAttribute("usernameExists", true);
					}
				if (userService.checkUsernameExists(customer.getUsername())) {
					model.addAttribute("usernameExists", true);
				}
				return "signup";
			}else {
				Set<UserRole> userRoles = new HashSet<>();
				userRoles.add(new UserRole(customer, roleDao.findByName("ROLE_USER")));
				
				userService.createUser(customer, userRoles);
				
				return "redirect:/";
			}
		}	
		
		@RequestMapping("/userFront")
		public String userFront(Principal principal, Model model) {
			Customer customer = userService.findByUsername(principal.getName());
			CheckingAccount checkingAccount = customer.getCheckingAccount();
			SavingsAccount savingsAccount = customer.getSavingsAccount();
			
			model.addAttribute("checkingAccount", checkingAccount);
			model.addAttribute("savingsAccount", savingsAccount);
			
			return "userFront";
		}
}
