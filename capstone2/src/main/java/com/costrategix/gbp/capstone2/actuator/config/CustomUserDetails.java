package com.costrategix.gbp.capstone2.actuator.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.costrategix.gbp.capstone2.actuator.model.User;



//@Component
@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {
	
	private User user;
	
	public CustomUserDetails(User user) {
		super();
		this.user= user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		int count=0;
		 List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		while(count<user.getUserRoles().size()) {
			roles.add(new SimpleGrantedAuthority("ROLE_"+user.getUserRoles().get(count).getRole().getRoleName()));
			System.out.println(user.getUserRoles().get(count).getRole().getRoleName());
			count++;
		}

		
//		return Collections.singleton(new SimpleGrantedAuthority(user.getRolename()));
		return roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stu
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
