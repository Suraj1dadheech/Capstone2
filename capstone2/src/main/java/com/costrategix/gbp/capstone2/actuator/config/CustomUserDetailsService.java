package com.costrategix.gbp.capstone2.actuator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.costrategix.gbp.capstone2.actuator.Dao.UserRepo;
import com.costrategix.gbp.capstone2.actuator.model.User;

//@Component
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	
	@Autowired
	private UserRepo userRepo; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}

}
