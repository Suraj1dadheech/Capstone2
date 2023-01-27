package com.costrategix.gbp.capstone2.actuator.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.costrategix.gbp.capstone2.actuator.DTO.AllUsersDto;
import com.costrategix.gbp.capstone2.actuator.DTO.UserDto;
//import com.costrategix.gbp.capstone2.actuator.DTO.ValidatorDto;
import com.costrategix.gbp.capstone2.actuator.Dao.AuthTokenRepo;
import com.costrategix.gbp.capstone2.actuator.Dao.UserRepo;
import com.costrategix.gbp.capstone2.actuator.exception.ResponseModel;
import com.costrategix.gbp.capstone2.actuator.exception.RestExceptionHandler;
import com.costrategix.gbp.capstone2.actuator.exception.UnauthorizedException;
//cos-repo.costrategix.net/gbp-projects/capstone-2-awsspp-be.git
import com.costrategix.gbp.capstone2.actuator.exception.UserNotFoundException;
import com.costrategix.gbp.capstone2.actuator.model.AuthToken;
import com.costrategix.gbp.capstone2.actuator.model.JsonTokenObject;
import com.costrategix.gbp.capstone2.actuator.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import io.jsonwebtoken.Jwts;

@Service
public class ValidatorService {
	
	private static final Logger logger=Logger.getLogger(ValidatorService.class);
	
	@Autowired
	private UserRepo userRepo;
	 
	@Autowired
    private JavaMailSender mailSender;
	

	 @Autowired
	 private AuthTokenRepo authRepo;
	 
	 	
	 @Autowired
	 private AuthTokenRepo AuthTokenRepo;
	 
	 @Autowired
	 private RestExceptionHandler restExceptionHandler;
	 

//	 @Autowired
//	 private factoryBean factorybean;
	 
	 @Value("${spring.mail.username}")
 	 String username;


    
    public void sendEmail(String to,String body,String subject) {
    	

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        this.mailSender.send(simpleMailMessage);
    }
    
   
    
    public String GenerateToken(String firstname, String email) {
		
		Instant now = Instant.now();
		String jwtToken = Jwts.builder()
		        .claim("firstname",firstname)
		        .claim("email",email)
		        .setId(UUID.randomUUID().toString())
		        .setIssuedAt(Date.from(now))
		        .setExpiration(Date.from(now.plus(7, ChronoUnit.DAYS)))
		        .compact();
		logger.info("GenerateToken() : Token generated");
		return jwtToken;
	}
    
    
    public String GenerateLoginToken(String firstname, String email) {
		
		Instant now = Instant.now();
		String jwtToken = Jwts.builder()
		        .claim("firstname",firstname)
		        .claim("email",email)
		        .setId(UUID.randomUUID().toString())
		        .setIssuedAt(Date.from(now))
		        .setExpiration(Date.from(now.plus(1, ChronoUnit.DAYS)))
		        .compact();
		logger.info("GenerateToken() : Token generated");
		return jwtToken;
	}
    
    public String GenerateResetPasswordToken(String firstname, String email) {
		
  		Instant now = Instant.now();
  		String jwtToken = Jwts.builder()
  		        .claim("firstname",firstname)
  		        .claim("email",email)
  		        .setId(UUID.randomUUID().toString())
  		        .setIssuedAt(Date.from(now))
  		        .setExpiration(Date.from(now.plus(48, ChronoUnit.MINUTES)))
  		        .compact();
  		logger.info("GenerateToken() : Token generated");
  		return jwtToken;
  	}
    
    public String checkNull(UserDto userDto) {
    	if(userDto.getFirstname()==null || userDto.getLastname()==null 
   		      || userDto.getRole()==null || userDto.getPhone()==null )
   			return "Some field are null";
    	return hasErrors(userDto);
    }
	
	public String hasErrors(UserDto userDto) {
		
		if((userDto.getFirstname().length()<2 || userDto.getFirstname().length()>10) ||
				userDto.getLastname().length()<2 || userDto.getLastname().length()>10 || !Pattern.matches("^[a-zA-Z]+", userDto.getFirstname()) || !Pattern.matches("^[a-zA-Z]+", userDto.getLastname()))
			return "for firstName and LastName minimum  2 and max 20 characters are allowed and Pattern should match!!!";
		
		int count=0;
		while(count<userDto.getPhone().size()) {
			if(!(Pattern.matches("^[+][1][0-9]{10}$", userDto.getPhone().get(count).getPhone_no())))
					return "US phone no. pattern doesn't match";
			count++;
		}
		return "clear";
		
	}


	
	 public void TokenSave(User user, String jwtToken) throws JsonMappingException, JsonProcessingException {
		  
		  AuthToken auth = new AuthToken();
		  
		  
		    String[] parts = jwtToken.split("\\.");
			
			byte[] decodedBytes = Base64.getDecoder().decode(parts[1]);
			String decodedString = new String(decodedBytes);
			

			
			ObjectMapper objectMapper = new ObjectMapper();
			
			JsonTokenObject tokenObjectJson = objectMapper.readValue(decodedString, JsonTokenObject.class);
			
		 	long expDate = Long.parseLong((String.valueOf(tokenObjectJson.getExp()))+"000");
			
			long iatDate = Long.parseLong((String.valueOf(tokenObjectJson.getIat()))+"000");
			

			
			
			ZonedDateTime exp = Instant.ofEpochMilli(expDate).atZone(ZoneOffset.UTC);
			
			ZonedDateTime iat = Instant.ofEpochMilli(iatDate).atZone(ZoneOffset.UTC);
		
					  
		  

		  auth.settokenvalue(jwtToken);
		  
		  auth.setcreateddatetime(iat);
		  
		  auth.setExpirationtime(exp);
		  
		  ZonedDateTime plusHours = iat.plusHours(10);
		  
		  auth.setEffectivetime(plusHours);
		  
		  auth.setUser(user);
		  
		  
		  
		  
		  int TokenValidity = exp.getDayOfMonth()-(plusHours.getDayOfMonth());
		  
		 auth.setTokenvalidity(TokenValidity);
		  
		  
		  
		  authRepo.save(auth);
		  
	  }


	
	 
	 public ResponseEntity<ResponseModel> checkValidity(String email) throws JsonMappingException, JsonProcessingException {
		 

	 	
		 AllUsersDto emailOfUser=userRepo.findByEmailDto(email);
		 if(emailOfUser==null) {
			 logger.info("user with the given mail id not found");
			 throw new UserNotFoundException("user not found");
		 }
	 	
		 Timestamp timeExp=authRepo.getExpirationDateDto(email);
		 if(timeExp.equals(null)) {
			 logger.error("user deleated from database");
			 throw new UserNotFoundException("user not found");
		 }
		 Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		 int compareTime=timeExp.compareTo(timeNow);
		 
		 if(compareTime>0) {							
				//return true;
				return restExceptionHandler.responseHandler("Invite link is valid");
			} else {				
				throw new UserNotFoundException("Invite link is expired contack admin for new link");
			}		 
		
	}
	
	 
	 

	public void TokenSave(User user, String jwtToken,int id)throws JsonMappingException, JsonProcessingException { {
		
		AuthToken auth = AuthTokenRepo.getTokenByUserId(id);
		  
		  
	    String[] parts = jwtToken.split("\\.");
		
		byte[] decodedBytes = Base64.getDecoder().decode(parts[1]);
		String decodedString = new String(decodedBytes);
		

		
		ObjectMapper objectMapper = new ObjectMapper();
		
		JsonTokenObject tokenObjectJson = objectMapper.readValue(decodedString, JsonTokenObject.class);
		
	 	long expDate = Long.parseLong((String.valueOf(tokenObjectJson.getExp()))+"000");
		
		long iatDate = Long.parseLong((String.valueOf(tokenObjectJson.getIat()))+"000");
		

		
		
		ZonedDateTime exp = Instant.ofEpochMilli(expDate).atZone(ZoneOffset.UTC);
		
		ZonedDateTime iat = Instant.ofEpochMilli(iatDate).atZone(ZoneOffset.UTC);

	
				  
	  

	  auth.settokenvalue(jwtToken);
	  
	  auth.setcreateddatetime(iat);
	  
	  auth.setExpirationtime(exp);
	  
	  ZonedDateTime plusHours = iat.plusHours(10);
	  
	  auth.setEffectivetime(plusHours);
	  
	  auth.setUser(user);
	  
	  
	  
	  
	  int TokenValidity = exp.getDayOfMonth()-(plusHours.getDayOfMonth());
	  
	 auth.setTokenvalidity(TokenValidity);
	  
	  
	  
	  authRepo.save(auth);
	  
		
	}
	
	}	
	
	public boolean isTokenValid(String token) throws JsonMappingException, JsonProcessingException {
//		LoginAuth loginAuth = new LoginAuth();

		String[] parts = token.split("\\.");

		byte[] decodedBytes = Base64.getDecoder().decode(parts[1]);
		String decodedString = new String(decodedBytes);

		ObjectMapper objectMapper = new ObjectMapper();

		JsonTokenObject tokenObjectJson = objectMapper.readValue(decodedString, JsonTokenObject.class);
		
		User user = userRepo.findByEmail(tokenObjectJson.getEmail()); 
		if(user==null)
			throw new UnauthorizedException("this token is not valid");

		long expDate = Long.parseLong((String.valueOf(tokenObjectJson.getExp())) + "000");

		ZonedDateTime exp = Instant.ofEpochMilli(expDate).atZone(ZoneOffset.UTC);
		
		Instant timeNow = Instant.now();
		
		ZonedDateTime zonedDateTime = timeNow
                .atZone(ZoneId.systemDefault());
		 
		System.out.println(exp);
		System.out.println(timeNow);
		
		
		 if(exp.compareTo(zonedDateTime)>0) 
			return false;

		return true;
	}
	
	
	
	
}