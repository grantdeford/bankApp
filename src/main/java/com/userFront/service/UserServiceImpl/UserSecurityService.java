package com.userFront.service.UserServiceImpl;



import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userFront.Dao.UserDao;
import com.userFront.domain.Customer;

@Service
public class UserSecurityService implements UserDetailsService{
		//Application logger
	private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = userDao.findByUsername(username);
		if(null == customer) {
			LOG.warn("Username {} not found", username);
			throw new UsernameNotFoundException("Username" + username + "not found");
		}
		return customer;
	}
}
