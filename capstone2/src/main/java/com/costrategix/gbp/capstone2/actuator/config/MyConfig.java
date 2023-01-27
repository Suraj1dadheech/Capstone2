package com.costrategix.gbp.capstone2.actuator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class MyConfig extends WebSecurityConfigurerAdapter {
		
	@Autowired
	public UserDetailsService getUserDetailService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(getUserDetailService);
	}
	
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//		daoAuthenticationProvider.setUserDetailsService(getUserDetailService);
//		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//		return daoAuthenticationProvider;
//	}

	//Configure Method
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider());
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
			.antMatchers("/**").permitAll()
			.and()
		.formLogin()
			.loginPage("/signin")
			.loginProcessingUrl("/dologin")
			.defaultSuccessUrl("/user/index")
//			.failureUrl("/login-fail")
			.and().csrf()
			.disable();



//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
//        authorizeRequests()	
//	      .antMatchers(HttpMethod.GET,"/role/user").hasAnyRole("System Admin","Event Coordinator","Driver")
//		  .antMatchers(HttpMethod.GET,"/role/admin").hasRole("System Admin")
//	      .antMatchers(HttpMethod.GET,"/home").permitAll()
//	      .antMatchers(HttpMethod.GET,"/**").permitAll()
//	      .antMatchers(HttpMethod.POST,"/User").permitAll().and().
//	      requestCache().requestCache(new NullRequestCache()).and().
//	        httpBasic().and().
//	        cors().and().
//	        csrf().disable();
			
		

	}
}
