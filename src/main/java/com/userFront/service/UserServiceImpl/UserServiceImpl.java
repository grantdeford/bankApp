package com.userFront.service.UserServiceImpl;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userFront.domain.Customer;
import com.userFront.domain.security.UserRole;
import com.userFront.Dao.RoleDao;
import com.userFront.Dao.UserDao;
import com.userFront.service.AccountService;
import com.userFront.service.UserService;
@Transactional
@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountService accountService;
	
	
	public void save(Customer customer) {
		userDao.save(customer);
	}
	public Customer findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	public Customer findByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
	public Customer createUser(Customer customer, Set<UserRole> userRoles) {
		Customer localUser = userDao.findByUsername(customer.getUsername());
		
		if(localUser != null) {
			LOG.info("Customer with username {} already exists. Can not comply.", customer.getUsername());
		}else {
			String encryptedPassword = passwordEncoder.encode(customer.getPassword());
			customer.setPassword(encryptedPassword);
			
			for(UserRole ur : userRoles) {
				roleDao.save(ur.getRole());
			}
			customer.getUserRoles().addAll(userRoles);
			
			customer.setCheckingAccount(accountService.createCheckingAccount());
			customer.setSavingsAccount(accountService.createSavingsAccount());
			
			localUser = userDao.save(customer);
		}
		return localUser;
	}
	public boolean checkUserExists(String username, String email) {
		if (checkUsernameExists(username) || checkEmailExists(username)) {
			return true;
		}else {
			return false;
		}
	}
	public boolean checkUsernameExists(String username) {
		if(null != findByUsername(username)) {
			return true;
		}
			return false;
	}
	public boolean checkEmailExists(String email) {
		if(null != findByEmail(email)) {
			return true;
		}
			return false;
	}
 }
