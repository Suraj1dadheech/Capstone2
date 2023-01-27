package com.costrategix.gbp.capstone2.actuator.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.costrategix.gbp.capstone2.actuator.DTO.CustomRolesDto;
import com.costrategix.gbp.capstone2.actuator.DTO.CustomUserDto;
import com.costrategix.gbp.capstone2.actuator.DTO.CustomUsersResponseDto;
import com.costrategix.gbp.capstone2.actuator.DTO.InvtreDto;
import com.costrategix.gbp.capstone2.actuator.DTO.TrailerDto;
import com.costrategix.gbp.capstone2.actuator.DTO.UserDto;
import com.costrategix.gbp.capstone2.actuator.Dao.AuthTokenRepo;
import com.costrategix.gbp.capstone2.actuator.Dao.CountryRepo;
import com.costrategix.gbp.capstone2.actuator.Dao.InventoryRepo;
import com.costrategix.gbp.capstone2.actuator.Dao.LocationRepo;
import com.costrategix.gbp.capstone2.actuator.Dao.PhoneRepo;
import com.costrategix.gbp.capstone2.actuator.Dao.PhoneTypeRepo;
import com.costrategix.gbp.capstone2.actuator.Dao.RoleRepo;
import com.costrategix.gbp.capstone2.actuator.Dao.TrailerRepo;
import com.costrategix.gbp.capstone2.actuator.Dao.UserRepo;
import com.costrategix.gbp.capstone2.actuator.Dao.UserRoleRepo;
import com.costrategix.gbp.capstone2.actuator.config.CustomConfig;
import com.costrategix.gbp.capstone2.actuator.exception.BusinessException;
import com.costrategix.gbp.capstone2.actuator.exception.ConflictException;
import com.costrategix.gbp.capstone2.actuator.exception.ForbiddenException;
import com.costrategix.gbp.capstone2.actuator.exception.NotFoundExceptions;
import com.costrategix.gbp.capstone2.actuator.exception.ResponseModel;
import com.costrategix.gbp.capstone2.actuator.exception.RestExceptionHandler;
import com.costrategix.gbp.capstone2.actuator.exception.UnauthorizedException;
import com.costrategix.gbp.capstone2.actuator.exception.UnprocessableException;
import com.costrategix.gbp.capstone2.actuator.model.CountryInfo;
import com.costrategix.gbp.capstone2.actuator.model.Inventory;
import com.costrategix.gbp.capstone2.actuator.model.JsonTokenObject;
import com.costrategix.gbp.capstone2.actuator.model.Location;
import com.costrategix.gbp.capstone2.actuator.model.LoginAuth;
import com.costrategix.gbp.capstone2.actuator.model.Phone;
import com.costrategix.gbp.capstone2.actuator.model.PhoneType;
import com.costrategix.gbp.capstone2.actuator.model.Role;
import com.costrategix.gbp.capstone2.actuator.model.Trailer;
import com.costrategix.gbp.capstone2.actuator.model.User;
import com.costrategix.gbp.capstone2.actuator.model.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {
	
	private static final Logger logger=Logger.getLogger(UserService.class);
	
	 @Autowired
	 private ValidatorService validateService;
	 

	 
	 @Autowired
	 private RestExceptionHandler restExceptionHandler;
	 
	 @Autowired
	 private UserRepo userRepo;
	 
	 @Autowired
	 private RoleRepo roleRepo;
	 
	 
	 @Autowired
	 private UserRoleRepo userRoleRepo;
	 
	 
	 @Autowired
	 private PhoneTypeRepo phoneTypeRepo;
	 
	 @Autowired
	 private PhoneRepo phoneRepo;
	 
	 
	 @Autowired
	 private AuthTokenRepo AuthTokenRepo;
	 	
	 
	 @Autowired
	 private LocationRepo locationRepo;
	 
	 @Autowired
	 private BCryptPasswordEncoder passwordEncoder;
	 
	 @Autowired
	 private TrailerRepo trailerRepo;
	 

	 @Autowired
	 private CountryRepo countryRepo;
	 
	 @Autowired
	 private CustomConfig customConfig;
	 
	 @Autowired
	 private InventoryRepo inventoryRepo;
	 

	 
	//--------------------------------Login-Checking Credentials------------------------------
		
		public ResponseEntity<ResponseModel> CheckCredentials(UserDto userDto) throws JsonMappingException, JsonProcessingException {
			User user = userRepo.findByEmail(userDto.getEmail());
			if(user==null)
				throw new BusinessException("User Doesn't exists");
			
			if(user.getPassword()==null) {
				throw new BusinessException("User is not Registered Yet!");
			}
			if(user.getStatus().equals("Locked")) {
			System.out.println("locked");
				throw new ForbiddenException("Your Account is Locked!");
			}
				
			if(passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
				//--------------------------Generating loginToken-----------------------------------------
				String jwtToken = validateService.GenerateLoginToken(user.getFirstName(),user.getEmail());
									
				LoginAuth loginAuth = new LoginAuth();
				  
				  
			    String[] parts = jwtToken.split("\\.");
				
				byte[] decodedBytes = Base64.getDecoder().decode(parts[1]);
				String decodedString = new String(decodedBytes);
				

				
				ObjectMapper objectMapper = new ObjectMapper();
				
				JsonTokenObject tokenObjectJson = objectMapper.readValue(decodedString, JsonTokenObject.class);
				
			 	long expDate = Long.parseLong((String.valueOf(tokenObjectJson.getExp()))+"000");
								
				ZonedDateTime exp = Instant.ofEpochMilli(expDate).atZone(ZoneOffset.UTC);
				 

				loginAuth.setLogintoken(jwtToken);
			  
				loginAuth.setExpirationtime(exp);
				loginAuth.setUser(user);
			  
			    user.setLoginAuth(loginAuth);

		//-------------------------------------login token end---------------------------------------
				
				user.setCount(0);
				userRepo.save(user);
				 int count=0;
					while(count<user.getUserRoles().size()) {
						if(user.getUserRoles().get(count).getRole().getRoleName().equals("System Admin")) {
							
							return restExceptionHandler.responseHandlerAccepted("login Successful",jwtToken);
						}
						count++;
					}
			 throw new UnauthorizedException("Sorry you are not Authorized");
			}
			else {
				int count = user.getCount();
				user.setCount(count+1);
				userRepo.save(user);
				if(count==4)
					throw new ForbiddenException("One more attempt is remaining please write the correct password");
				if(count>=5) {
					user.setStatus("Locked");
					user.setCount(0);
					userRepo.save(user);
					throw new ForbiddenException("Your Account is Locked!");
				}
					
			   throw new UnauthorizedException("password not valid : "+count);
			}
			
		}

	 
	 //--------------------------Invite new User-------------------------------------------
	
	 public ResponseEntity<ResponseModel> InviteNewUser(UserDto userDto,String token) throws JsonMappingException, JsonProcessingException {
//		 if(validateService.isTokenValid(token)) 
//			 throw new UnauthorizedException("Sorry Token is expired please login again");


		 User user = new User();
		 user = userRepo.findByEmail(userDto.getEmail());		 
			 
		 if(userDto.getEmail()==null)
			 throw new BusinessException("Please! Enter the mailId");
	
		 if(user!=null) {
			 if(user.getExist()==1)
				 throw new ConflictException("mail Id is already registered");
			user = ExistingUserInvoke(userDto, user.getUserId());
			return restExceptionHandler.CreatedResponseHandler("Saved Succesfully");
				
		 }
		 
			if (!validateService.checkNull(userDto).equals("clear")) {
				String errorMessage = validateService.checkNull(userDto);
				logger.error("InviteNewUser() :"+errorMessage);
				throw new UnprocessableException(errorMessage);
			}

		try 
		{	
				user = ModelToClassSave(userDto);
			
		
		  
		  
		   String jwtToken = validateService.GenerateToken(userDto.getFirstname(),userDto.getEmail());

		   validateService.TokenSave(user,jwtToken);
		   
		   String body = "Succesfully registered please set the password :"+"https://capstone2webdev.z13.web.core.windows.net/register/"+userDto.getEmail();
		   String subject = "Regarding registration process";
           
		   validateService.sendEmail(userDto.getEmail(),body,subject);

		
		} catch (Exception e) {
			logger.error("InviteNewUser() :error while saving data");
			throw new NotFoundExceptions(e.getMessage());
		}
		
		logger.info("InviteNewUser() :data saved, token generated and mail sent");
		return restExceptionHandler.CreatedResponseHandler("Saved Succesfully");
	}
	 
	 
	 //--------------------------Update existing(Active/InActive) User-------------------------------------------
	 


	public ResponseEntity<ResponseModel> UpdateUser(UserDto userDto, int id,String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		 int count;
			User user = userRepo.findById(id);
			
			if(user==null) {
				logger.error("UpdateUser() : Id doesn't exists");
				throw new BusinessException("Id doesn't exists");
			}
			
						
			
			if(userDto.getFirstname()!=null) {
				if(userDto.getFirstname().length()<2 || userDto.getFirstname().length()>10
													 || !Pattern.matches("^[a-zA-Z]+", userDto.getFirstname())) {
					logger.error("UpdateUser() : first Name length and pattern should match");
					throw new UnprocessableException("first Name length and pattern should match");
				}
					
				user.setFirstName(userDto.getFirstname());
			}

				
			if (userDto.getLastname() != null) {
				if (userDto.getLastname().length() < 2 || userDto.getLastname().length() > 10
						|| !Pattern.matches("^[a-zA-Z]+", userDto.getLastname())) {
					logger.error("UpdateUser() : LastName length and pattern should match");
					throw new UnprocessableException("LastName length and pattern should match");
				}
				user.setLastName(userDto.getLastname());
			}
			
			
		 try {
			
			 logger.info("Update():user value Updated");
			 user.setLastseen(Instant.ofEpochMilli(Instant.now().toEpochMilli()).atZone(ZoneOffset.UTC));
			 userRepo.save(user);	
			 
		  Role role = new Role();
		  PhoneType phoneType = new PhoneType();
			
			if (userDto.getFirstname() != null) {
				String jwtToken = validateService.GenerateToken(user.getFirstName(), user.getEmail());
				validateService.TokenSave(user, jwtToken, id);
			}



			if (userDto.getRole() != null) {
				  count = 0;
				  userRoleRepo.deleteRoleByUserId(id);
				  List<UserRole> u_roles = new ArrayList<>();
				  while(count<userDto.getRole().size()) {
					  UserRole user_Role  = new UserRole();
					  role=roleRepo.findByName(userDto.getRole().get(count).getRoleName());
					  user_Role.setUser(user);
					  user_Role.setRole(role);
					  u_roles.add(user_Role);
					  count++;
					  break;
				  }
				  
				  user.setUserRoles(u_roles);

			}
				  
			if (userDto.getPhone() != null) {
				
						count=0;
						while(count<userDto.getPhone().size()) {
							if(!(Pattern.matches("^[+][1][0-9]{10}$", userDto.getPhone().get(count).getPhone_no())))
								throw new NotFoundExceptions("US phone numbers only");
							count++;
			}
				phoneRepo.deletePhonesByUserId(id);		
						  count=0;
							 List<Phone> phones = new ArrayList<>();
						  while(count<userDto.getPhone().size()) {
							 
						    phoneType = phoneTypeRepo.findByPhoneType(userDto.getPhone().get(count).getPhoneType());

							 Phone phone1 = new Phone();
							 String value = userDto.getPhone().get(count).getPhone_no();
							  phone1.setPhone_no(value);
							  phone1.setUser(user);
							  phone1.setPhoneType(phoneType);
							  phone1.setSmsAllowed(userDto.getPhone().get(count).getSmsAllowed());
							  phones.add(phone1);
							  count++;
						 }
						  
						  user.setPhones(phones);
			
			}
				 userRepo.save(user);
		 }catch (Exception e) {
				logger.error("UpdateUser() :error while updating data");
				throw new NotFoundExceptions(e.getMessage());
			}

				
			logger.info("UpdateUser() : Data updated successfully");	  
				 return restExceptionHandler.responseHandler("Updated");

		}

	 
	 //--------------------------Register user by given link-------------------------------------------
	 
	public ResponseEntity<ResponseModel> RegisterUser(UserDto userDto) {
		
		
		
		Timestamp exp = AuthTokenRepo.getExpirationDateDto(userDto.getEmail());
		if(exp==null)
			throw new BusinessException("This Mail-Id is not registered");
		
		try {
			User user=new User();
			Timestamp timeNow = new Timestamp(System.currentTimeMillis());
			 
			 if(exp.compareTo(timeNow)<0) {						
				 int authId = AuthTokenRepo.findAuthIdbyUserEmail(userDto.getEmail());
				 user = userRepo.findByEmail(userDto.getEmail());
				 phoneRepo.deletePhonesByUserId(user.getUserId());
				 userRoleRepo.deleteRoleByUserId(user.getUserId());
				 AuthTokenRepo.deleteAuthTokenById(authId);
				 user.setExist(0);
				 userRepo.save(user);				 
					throw new UnauthorizedException("expired");
		 }
		 
		user = userRepo.findByEmail(userDto.getEmail());
		
		
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setLastseen(Instant.ofEpochMilli(Instant.now().toEpochMilli()).atZone(ZoneOffset.UTC));
		user.setStatus("Active");
		
		userRepo.save(user);
		
		} catch (Exception e) {
			logger.error("RegisterUser() :error while Registering user");
			throw new UnauthorizedException("expired");
		}
		
		
		
		return restExceptionHandler.responseHandler("Registered Successfully");
	
	}
	
	
	
	
//........................................Resend Invite-------------------------------------
	public ResponseEntity<ResponseModel> ResendInvite(UserDto userDto,String token) throws JsonMappingException, JsonProcessingException  {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		
		try {
			
		User user = userRepo.findByEmail(userDto.getEmail());
		 int authId = AuthTokenRepo.findAuthIdbyUserEmail(userDto.getEmail());
		 
		 AuthTokenRepo.deleteAuthTokenById(authId);
		 
		String jwtToken = validateService.GenerateToken(user.getFirstName(), user.getEmail());
		validateService.TokenSave(user, jwtToken);

		String subject="New Invitation";
		
		String body = "new link for registration :"+"https://capstone2webdev.z13.web.core.windows.net/register/"+userDto.getEmail();
        
		validateService.sendEmail(userDto.getEmail(),body,subject);
		
		} catch (Exception e) {
			logger.error("ResendInvite() :error while Sending invite");
			throw new NotFoundExceptions(e.getMessage());
		}
		
		logger.info("InviteNewUser() :Resent Invite Sent and new Token generated !");
		return restExceptionHandler.responseHandler("Resent Invite Sent !");
	}
		
	
	//........................................resend passwordlink-------------------------------------
	public ResponseEntity<ResponseModel> ResendPasswordLink(UserDto userDto){		
		 try {
			 
		 User user = userRepo.findByEmail(userDto.getEmail());
		 
		 String jwt = validateService.GenerateResetPasswordToken(userDto.getFirstname(),userDto.getEmail());
		 		 
		 
		 String body = "Hey ! "+user.getFirstName()+" please change your password at:\n"+"https://capstone2webdev.z13.web.core.windows.net/register/"+userDto.getEmail()+"?ott="+jwt;
		 
		 
		 String subject="Reset Password"; 
			
	        
			validateService.sendEmail(userDto.getEmail(),body,subject);
		 
		} catch (Exception e) {
			logger.error("ResendPasswordLink() :error while Sending password link");
			throw new NotFoundExceptions(e.getMessage());
		}
		
		logger.info("ResendPasswordLink() :Resent password link Sent !");
		return restExceptionHandler.responseHandler("Reset password link sent!"); 
	 }
	
	
	
	//----------------------------------accept the reset passwordLink--------------------
	public ResponseEntity<ResponseModel> AcceptResentPasswordFromMail(UserDto userDto,String token){
		 try {
			 User user = userRepo.findByEmail(userDto.getEmail());
			 //--------------------------------------------------------
			 if(validateService.isTokenValid(token)) 
				 throw new UnauthorizedException("Sorry Token is expired please Contact Admin");
			 
			 user.setPassword(passwordEncoder.encode(userDto.getPassword()));
			 userRepo.save(user);
		
		 
		} catch (Exception e) {
			logger.error("AcceptResentPasswordFromMail() :error while AcceptResentPasswordFromMail");
			throw new NotFoundExceptions(e.getMessage());
		}
		
		logger.info("AcceptResentPasswordFromMail() :success while AcceptResentPasswordFromMail");
		return restExceptionHandler.responseHandler("Password Reset Succesfully !");
		 
	 }
	
	
	//--------------------------------------------File Upload and Adding Location----------------------------
		public ResponseEntity<ResponseModel> AddLocation(Location location,String path, MultipartFile file,String token) throws IOException  {
			 if(validateService.isTokenValid(token)) 
				 throw new UnauthorizedException("Sorry Token is expired please login again");
			 
			 try {
			
			if(file != null) {
				String name = file.getOriginalFilename();
				String filePath = path+ File.separator+name;
				File f = new File(path);
					if(!f.exists()) {
						f.mkdir();
					}
				Files.copy(file.getInputStream(), Paths.get(filePath));
				location.setPhoto(filePath);
			}
			locationRepo.save(location);
			 }catch(Exception e) {
				 throw new ConflictException("Image is already exist");
			 }
			
			 return restExceptionHandler.responseHandler("Location Saved");
		}
	
	
	//....................................EDIT LOCATION................................
	

	public ResponseEntity<ResponseModel> UpdateLocation(Location location, String path,int id, MultipartFile file,String token) {
		
			Location location1 = locationRepo.findById(id);
			if(location1==null)
				throw new BusinessException("Id is not registered");
			
			try {
				if(file != null) {
				File f= new File(location1.getPhoto());   
				f.delete();
				
				String name = file.getOriginalFilename();
				String filePath = path+ File.separator+name;
				f = new File(path);
				if(!f.exists()) {
					f.mkdir();
				}
				
				Files.copy(file.getInputStream(), Paths.get(filePath));
				location1.setPhoto(filePath);			
				}
			
			if(location.getLocationType()!=null)location1.setLocationType(location.getLocationType());
			if(location.getLocationname()!=null)location1.setLocationname(location.getLocationname());
			if(location.getAddress()!=null)location1.setAddress(location.getAddress());
			if(location.getAddress2()!=null)location1.setAddress2(location.getAddress2());
			if(location.getCity()!=null)location1.setCity(location.getCity());
			if(location.getState()!=null)location1.setState(location.getState());
			if(location.getCountry()!=null)location1.setCountry(location.getCountry());
			if(location.getZip()!=0)location1.setZip(location.getZip());
			if(location.getvCoordinates()!=null)location1.setvCoordinates(location.getvCoordinates());
			if(location.gethCoordinates()!=null)location1.sethCoordinates(location.gethCoordinates());
			if(location.getComments()!=null)location1.setComments(location.getComments());
				
			
			locationRepo.save(location1);
			
		}catch(Exception e) {
			logger.error("EditLocation() :error while editing location Updated successfully !");
			throw new NotFoundExceptions(e.getMessage());
		}
			
			logger.info("EditLocation() :Location Updated successfully !");
			return restExceptionHandler.responseHandler("EditLocation() :Location Updated successfully !");
		
			
	}
	
	
	//------------------------------post api to add trailer----------------------------------------
	

	public ResponseEntity<ResponseModel> AddTrailer(Trailer trailer, String path, MultipartFile file,String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
				
		String name = file.getOriginalFilename();
		
		String filePath = path+ File.separator+name;
		File f = new File(path);
		
		if(!f.exists()) {
			f.mkdir();
		}
		
		//checking if all fields are there
		if((trailer.getGroupColor()==null)||(trailer.getTrailerNo()==null)||(trailer.getTrailerName()==null)
			||(trailer.getTrailerWeight()==0)||(trailer.getTractorWeight()==0)||(trailer.getSkyBitzNo()==null)
			||(trailer.getTrailertype()==null)) {
			logger.error("Some fields are missing");
			throw new NotFoundExceptions("Some fields are missing please enter all the fields");
		}
		
		
		//checking color matched or not
		String color=trailer.getGroupColor().toString();
		List<String> listColor=new ArrayList<String>(Arrays.asList("Pink",
		"Red","Orange","Yellow","Green","Teal","Blue","Purple","Brown"));
				
		int colorMatched=0;
		for(int i=0;i<listColor.size();i++) {
			if(color.equals(listColor.get(i))) {
					colorMatched+=1;
					break;
				}
		}
				
		if (colorMatched==0) {
				throw new ConflictException("select the correct color");
		}
		
		//checking trailer number is unique or not
		int trailerNoUniqueChk=0;
		String trailerNo=trailer.getTrailerNo().toString();
		List<String> trailerNoList=new ArrayList<>();
		trailerNoList=trailerRepo.getAllTrailerNo();
		for (String str : trailerNoList) {
				if(str.equals(trailerNo)) {
					trailerNoUniqueChk+=1;
					break;
				}
		}
				
		if(trailerNoUniqueChk>0) {
			throw new ConflictException("Enter the unique trailer number");
		}
		
		//checking Trailer Type is correct or not
		int trailerTypChk=0;
		String trailerTyp=trailer.getTrailertype().toString();
		List<String> trailerTypList=new ArrayList<>(Arrays.asList("Gear",
				"Prolyte","Delay","Steel","Poles","V1"));
		
		for (String str : trailerTypList) {
				if(str.equals(trailerTyp)) {
					trailerTypChk+=1;
					break;
				}
		}

		if(trailerTypChk==0) {
			logger.error("Enterd wrong trailer type");
			throw new NotFoundExceptions("Enter the correct trailer type");
		}
		
		
		//checking unique skybitzno
		int skyBitzNoUniqueChk=0;
		String skybtz=trailer.getSkyBitzNo().toString();
		List<String> skyBtzNoList=new ArrayList<>();
		skyBtzNoList=trailerRepo.getAllSkyBtzNo();
		for (String str : skyBtzNoList) {
			if(str.equals(skybtz)) {
				skyBitzNoUniqueChk+=1;
				break;
			}
		}
				
		if(skyBitzNoUniqueChk>0) {
				throw new ConflictException("Enter the unique skybitz number");
		}
		
		//increase the tracktor_value by one in inventory
		inventoryRepo.increaseTracktorCount(trailer.getGroupColor().toString());
		
		
		
		try {
				Files.copy(file.getInputStream(), Paths.get(filePath));
				trailer.setImage(filePath);
				
				trailerRepo.save(trailer);
			} catch (IOException e) {
				throw new NotFoundExceptions(e.getMessage());
			
			}
		
		 return restExceptionHandler.responseHandler("trailer Saved");
		
		
	}

			
//-------------------------------------------Delete Api to delete trailer images------------------------------
	public ResponseEntity<ResponseModel> DeleteTrailerImage(int id,String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		try {
			Trailer trailer = trailerRepo.findTrailerById(id);
		
			if(trailer.getImage()==null)
				throw new UnprocessableException("Image is not present for this trailer");
			
			File f= new File(trailer.getImage());   
			f.delete(); 
        		trailer.setSoftdelete(1);
        		trailer.setImage(null);
				trailerRepo.save(trailer);
		}catch(Exception e) {
			logger.error("DeleteTrailer() :error while deleting trailer!");
			throw new NotFoundExceptions(e.getMessage());
		}			
			
		logger.info("DeleteTrailer() :Trailer deleted successfully !");
		return restExceptionHandler.responseHandler("Trailer deleted successfully !");
}
	
	
	//-------------------------------------------UpdateTrailer Api to update trailer info------------------------------
	
	public ResponseEntity<ResponseModel> UpdateTrailer(Trailer trailer,	int id,String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		try {
			Trailer trailer1 = trailerRepo.findTrailerById(id);
			if(trailer1.getSoftdelete()==1)
				throw new UnprocessableException("Trailer is deleted can't be updated");
	
			
			
//			if(trailer.getGroupColor()!=null)trailer1.setGroupColor(trailer.getGroupColor());
			if(trailer.getTrailerNo()!=null)trailer1.setTrailerNo(trailer.getTrailerNo());
			if(trailer.getTrailerName()!=null)trailer1.setTrailerName(trailer.getTrailerName());
			if(trailer.getTrailerWeight()!=0)trailer1.setTrailerWeight(trailer.getTrailerWeight());
			if(trailer.getTractorWeight()!=0)trailer1.setTractorWeight(trailer.getTractorWeight());
			if(trailer.getSkyBitzNo()!=null)trailer1.setSkyBitzNo(trailer.getSkyBitzNo());
			if(trailer.getComment()!=null)trailer1.setComment(trailer.getComment());
			if(trailer.getTrailertype()!=null)trailer1.setTrailertype(trailer.getTrailertype());
			
			if(trailer.getInventoryId()!=0) {
				Inventory inventory = inventoryRepo.findById(trailer.getInventoryId()).get();
				inventory.setTractorCount(inventory.getTractorCount()+1);
				trailer1.setGroupColor(inventory.getGroupcolor());
				trailer1.setInventoryId(trailer.getInventoryId());
				inventoryRepo.save(inventory);
			}
			
			
			trailerRepo.save(trailer1);
			
		}catch(Exception e) {
			logger.error("UpdateTrailer() :error while updating trailer!");
			throw new NotFoundExceptions(e.getMessage());
		}			
			
			logger.info("UpdateTrailer() :Trailer Updated successfully !");
			return restExceptionHandler.responseHandler("UpdateTrailer() :Trailer Updated successfully !");
	}
	
	

	//------------------------------Patch api to Update trailer image----------------------------------------
	
	public ResponseEntity<ResponseModel> UpdateTrailerImage(MultipartFile file,String path, int id,String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		
		try {
			
			Trailer trailer  = trailerRepo.findTrailerById(id);
			File f
            = new File(trailer.getImage());
 
	        f.delete();
	        
	        String name = file.getOriginalFilename();
			String filePath = path+ File.separator+name;
			f = new File(path);
				
			Files.copy(file.getInputStream(), Paths.get(filePath));
			trailer.setImage(filePath);
			trailerRepo.save(trailer);
			
			
		} catch (Exception e) {
			logger.error("UpdateTrailerImage() :Error while Trailer image Update !");
			throw new NotFoundExceptions(e.getMessage());
			
		}
		
		logger.info("UpdateTrailerImage() :Trailer image Updated successfully !");
		return restExceptionHandler.responseHandler("UpdateTrailerImage() :Trailer image updated successfully !");
	}
	
	
	//----------------------------------------------Get APIs-----------------------------------------
	
	 
	 
	 public List<CustomUsersResponseDto> getAllUsers(String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		
			List<CustomUserDto> listAllUsers=new ArrayList<>();
			listAllUsers=userRepo.findUserByCustomUserDto();
			
			if(listAllUsers.isEmpty()) {
				logger.error("empty list nothing to display no users in database");				
				throw new UnprocessableException("No users found");
			}
			
			//List<CustomRolesDto> roleList=new ArrayList<>();
			//List<CustomPhoneDto> phoneList=new ArrayList<>();
			List<CustomUsersResponseDto> responseList=new ArrayList<>();
			for(CustomUserDto user:listAllUsers) {
				CustomUsersResponseDto customUsersResponseDto=new CustomUsersResponseDto();
				customUsersResponseDto.setUserId(user.getUserId());
				customUsersResponseDto.setEmail(user.getEmail());
				customUsersResponseDto.setFirstName(user.getFirstName());
				customUsersResponseDto.setLastName(user.getLastName());
				customUsersResponseDto.setStatus(user.getStatus());
				customUsersResponseDto.setLastSeen(user.getLastSeen());
				customUsersResponseDto.setRoleDetails(roleRepo.findAllRolesByUserId(user.getUserId()));
				customUsersResponseDto.setPhoneDetails(phoneRepo.findAllphoneNos(user.getUserId()));
				
				responseList.add(customUsersResponseDto);
				
				
			}
			
			if(responseList.isEmpty()) {
				logger.error("empty list nothing to display no users in database");
				//throw new BusinessException("404","empty list");
				throw new NotFoundExceptions("No users found");
			}
			return responseList;
			
		}
	 
	 public List<CustomUsersResponseDto> filterAllUsersByStatus(int statusId,String token) throws JsonMappingException, JsonProcessingException{
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		 List<CustomUserDto> listAllUsers=new ArrayList<>();
		 listAllUsers=userRepo.findUserByCustomUserDto();
		 
		 if(listAllUsers.isEmpty()) {
				logger.error("empty list nothing to display no users in database");
				throw new UnprocessableException("No users found");
			}
			
			//List<CustomRolesDto> roleList=new ArrayList<>();
			//List<CustomPhoneDto> phoneList=new ArrayList<>();
			List<CustomUsersResponseDto> responseList=new ArrayList<>();
			List<CustomUsersResponseDto> responseList1=new ArrayList<>();
			for(CustomUserDto user:listAllUsers) {
				CustomUsersResponseDto customUsersResponseDto=new CustomUsersResponseDto();
				customUsersResponseDto.setUserId(user.getUserId());
				customUsersResponseDto.setEmail(user.getEmail());
				customUsersResponseDto.setFirstName(user.getFirstName());
				customUsersResponseDto.setLastName(user.getLastName());
				customUsersResponseDto.setStatus(user.getStatus());
				customUsersResponseDto.setLastSeen(user.getLastSeen());
				customUsersResponseDto.setRoleDetails(roleRepo.findAllRolesByUserId(user.getUserId()));
				customUsersResponseDto.setPhoneDetails(phoneRepo.findAllphoneNos(user.getUserId()));
				responseList.add(customUsersResponseDto);
				
			}
			
			for(CustomUsersResponseDto status:responseList) {
				if(statusId==1) {
					if(status.getStatus().equals("Invited")) {
						System.out.println("1");
						responseList1.add(status);
					}
				}else if(statusId==2) {
					if(status.getStatus().equals("Active")) {
						System.out.println("2");
						responseList1.add(status);
					}
				}else if(statusId==3){
					if(status.getStatus().equals("Locked")) {
						System.out.println("3");
						responseList1.add(status);
					}
				}
			}
		 
			if(responseList1.isEmpty()) {
				logger.error("empty list nothing to display no users in database");
				
				throw new UnprocessableException("No users found");
			}
		 return responseList1;
	 }
	 
	 public List<CustomUsersResponseDto> filterAllUsersByRole(int roleId,String token) throws JsonMappingException, JsonProcessingException{
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		 List<CustomUserDto> listAllUsers=new ArrayList<>();
		 listAllUsers=userRepo.findUserByCustomUserDto();
		 
		 if(listAllUsers.isEmpty()) {
				logger.error("empty list nothing to display no users in database");
				//throw new BusinessException("404","empty list");
				throw new UnprocessableException("No users found");
			}
			
			//List<CustomRolesDto> roleList=new ArrayList<>();
			//List<CustomPhoneDto> phoneList=new ArrayList<>();
			List<CustomUsersResponseDto> responseList=new ArrayList<>();
			List<CustomUsersResponseDto> responseList1=new ArrayList<>();
			for(CustomUserDto user:listAllUsers) {
				CustomUsersResponseDto customUsersResponseDto=new CustomUsersResponseDto();
				customUsersResponseDto.setUserId(user.getUserId());
				customUsersResponseDto.setEmail(user.getEmail());
				customUsersResponseDto.setFirstName(user.getFirstName());
				customUsersResponseDto.setLastName(user.getLastName());
				customUsersResponseDto.setStatus(user.getStatus());
				customUsersResponseDto.setLastSeen(user.getLastSeen());
				customUsersResponseDto.setRoleDetails(roleRepo.findAllRolesByUserId(user.getUserId()));
				customUsersResponseDto.setPhoneDetails(phoneRepo.findAllphoneNos(user.getUserId()));
				responseList.add(customUsersResponseDto);
				//System.out.println(phoneRepo.findAllphoneNos(user.getUserId()));
								
				//System.out.println(roleRepo.findAllRolesByUserId(user.getUserId()).get(0).getRoleName());
				
				
			}
			
			for(CustomUsersResponseDto roles:responseList) {
				List<CustomRolesDto> rolesList=new ArrayList<>();
				rolesList=roles.getRoleDetails();
				//System.out.println(rolesList);
				if(roleId==1) {
					for(CustomRolesDto roles1:rolesList) {
					//System.out.println(roles1.getRoleName());
						if(roles1.getRoleName().equals("System Admin")) {
							responseList1.add(roles);
						}
					}
				}else if (roleId==2) {
					for(CustomRolesDto roles1:rolesList) {
						//System.out.println(roles1.getRoleName());
							if(roles1.getRoleName().equals("Event Coordinator")) {
								responseList1.add(roles);
							}
						}
				}else if(roleId==3){
					for(CustomRolesDto roles1:rolesList) {
						//System.out.println(roles1.getRoleName());
							if(roles1.getRoleName().equals("Driver")) {
								responseList1.add(roles);
							}
						}
					
				}
			}
			if(responseList1.isEmpty()) {
				logger.error("empty list nothing to display no users in database");
				throw new UnprocessableException("No users found");
			}
		 
		 return responseList1;
	 }
	 
	 public List<Location> getAllLocationInfo(String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
			List<Location> locationList=new ArrayList<>();
			locationList=locationRepo.findAll();
			if (locationList.isEmpty()) {
				logger.info("locations not found");
				throw new UnprocessableException("locations not found");			
			}
			return locationList;
		}

	 


		public Optional<Location> getAllLocationInfoById(int id,String token) throws JsonMappingException, JsonProcessingException {
			 if(validateService.isTokenValid(token)) 
				 throw new UnauthorizedException("Sorry Token is expired please login again");
			 
			Optional<Location> locations;
			locations=locationRepo.findByLocationId(id);
		
			if(locations.isEmpty()) {
				logger.info("location not found");				
				throw new UnprocessableException("Location not found");
			}
			return locations;
		}
		
		
		public @ResponseBody byte[] getLocationImageById(int id,String token) throws JsonMappingException, JsonProcessingException {
			 if(validateService.isTokenValid(token)) 
				 throw new UnauthorizedException("Sorry Token is expired please login again");
			 
			Optional<Location> locations;
			locations=locationRepo.findByLocationId(id);
			
		
			if(locations.isEmpty()) {
				logger.info("location not found");				
				throw new UnprocessableException("Location not found");
			}
			return null;
//		     
//		     InputStream in = getClass()
//		    	      .getResourceAsStream(locations.get().getPhoto());
//		    	    return IOUtils.toByteArray(in);
			
		}
		
	
		
		public List<String> getLocationTypes(String token) throws JsonMappingException, JsonProcessingException {
			 if(validateService.isTokenValid(token)) 
				 throw new UnauthorizedException("Sorry Token is expired please login again");
			//List<String> locationTypes=new ArrayList<String>(Arrays.asList("Stadium","Arena","Storage"));			
			//return locationTypes;
			List<String> locationTypes=customConfig.typesOfLocations();
			return locationTypes;
		}
	 
	 
	 //............................Dto to UserTable------------------------
	 public User ModelToClassSave(UserDto userDto) {
		  User user = new User();		
		try {

		  Role role = new Role();
		  PhoneType phoneType = new PhoneType();
		  
		  
		  user.setFirstName(userDto.getFirstname());
		  user.setLastName(userDto.getLastname());
		  user.setEmail(userDto.getEmail());
		  user.setStatus("Invited");
		  user.setLastseen(Instant.ofEpochMilli(Instant.now().toEpochMilli()).atZone(ZoneOffset.UTC));
		  user.setExist(1);
		  user.setCount(0);
			  
		  int count=0;
			 List<Phone> phones = new ArrayList<>();
		  while(count<userDto.getPhone().size()) {
			 
		    phoneType = phoneTypeRepo.findByPhoneType(userDto.getPhone().get(count).getPhoneType());

			 Phone phone1 = new Phone();
			 String value = userDto.getPhone().get(count).getPhone_no();
			  phone1.setPhone_no(value);
			  phone1.setUser(user);
			  phone1.setPhoneType(phoneType);
			  phone1.setSmsAllowed(userDto.getPhone().get(count).getSmsAllowed());
			  phones.add(phone1);
			  count++;
		 }
		  
		  user.setPhones(phones);
		  
		  
		  count = 0;
		  List<UserRole> u_roles = new ArrayList<>();
		  while(count<userDto.getRole().size()) {
			  UserRole user_Role  = new UserRole();
			  role=roleRepo.findByName(userDto.getRole().get(count).getRoleName());
			  user_Role.setUser(user);
			  user_Role.setRole(role);
			  u_roles.add(user_Role);
			  count++;
			  break;
		  }
		  
		  user.setUserRoles(u_roles);
		  userRepo.save(user);
		  
		} catch (Exception e) {
			logger.error("ModelToClassSaving() :error while saving data");
			throw new NotFoundExceptions(e.getMessage());
		}
		  
		  logger.info("ModelToClassSaving() : data saved ");
		  return user;
		
		
	}


	//--------------------------------------invoking deleted user------------------------------------ 
public User ExistingUserInvoke(UserDto userDto, int id) throws JsonMappingException, JsonProcessingException {
		 
			int count;
			User user = userRepo.findById(id);

					
			if(userDto.getFirstname()!=null) {
				if(userDto.getFirstname().length()<2 || userDto.getFirstname().length()>10
													 || !Pattern.matches("^[a-zA-Z]+", userDto.getFirstname())) {
					logger.error("UpdateUser() : first Name length and pattern should match");
					throw new UnprocessableException("first Name length and pattern should match");
				}
					
				user.setFirstName(userDto.getFirstname());
			}

				
			if (userDto.getLastname() != null) {
				if (userDto.getLastname().length() < 2 || userDto.getLastname().length() > 10
						|| !Pattern.matches("^[a-zA-Z]+", userDto.getLastname())) {
					logger.error("UpdateUser() : LastName length and pattern should match");
					throw new UnprocessableException("LastName length and pattern should match");
				}
				user.setLastName(userDto.getLastname());
			}
			
			
		 try {
			
			 logger.info("ExistingUserInvoke():user value Updated");
			 user.setLastseen(Instant.ofEpochMilli(Instant.now().toEpochMilli()).atZone(ZoneOffset.UTC));
			 userRepo.save(user);	
			 
		  Role role = new Role();
		  PhoneType phoneType = new PhoneType();
			
			if (userDto.getFirstname() != null) {
				String jwtToken = validateService.GenerateToken(user.getFirstName(), user.getEmail());
				validateService.TokenSave(user, jwtToken);
			}



			if (userDto.getRole() != null) {
				  count = 0;
				  List<UserRole> u_roles = new ArrayList<>();
				  while(count<userDto.getRole().size()) {
					  UserRole user_Role  = new UserRole();
					  role=roleRepo.findByName(userDto.getRole().get(count).getRoleName());
					  user_Role.setUser(user);
					  user_Role.setRole(role);
					  u_roles.add(user_Role);
					  count++;
				  }
				  
				  user.setUserRoles(u_roles);

			}
				  
			if (userDto.getPhone() != null) {
				
						count=0;
						while(count<userDto.getPhone().size()) {
							if(!(Pattern.matches("^[+][1][0-9]{10}$", userDto.getPhone().get(count).getPhone_no())))
								throw new UnprocessableException("US phone numbers only");
							count++;
			}
						
						  count=0;
							 List<Phone> phones = new ArrayList<>();
						  while(count<userDto.getPhone().size()) {
							 
						    phoneType = phoneTypeRepo.findByPhoneType(userDto.getPhone().get(count).getPhoneType());

							 Phone phone1 = new Phone();
							 String value = userDto.getPhone().get(count).getPhone_no();
							  phone1.setPhone_no(value);
							  phone1.setUser(user);
							  phone1.setPhoneType(phoneType);
							  phone1.setSmsAllowed(userDto.getPhone().get(count).getSmsAllowed());
							  phones.add(phone1);
							  count++;
						 }
						  
						  user.setPhones(phones);
			
			}
				user.setExist(1);
				 userRepo.save(user);
		 }catch (Exception e) {
				logger.error("ExistingUserInvoke() :error while ExistingUserInvoke");
				throw new NotFoundExceptions(e.getMessage());
			}

				
			logger.info("ExistingUserInvoke() : Data updated successfully in ExistingUserInvoke");	  
				 return user;

		}



	public CountryInfo getCountryDetailsByZip(String zip,String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		CountryInfo countryInfo1=new CountryInfo();
		countryInfo1=countryRepo.findCountryInfoByZip(zip);
		
		return countryInfo1;
	}


	//-------------get states by country--------------------------------------------------		

	public List<String> getCountryDetails(String country,String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");

		List<String> countryInfo2=new ArrayList<>();
		countryInfo2=countryRepo.findCountryInfo(country);
		if(countryInfo2.isEmpty()) {
			logger.info("Country database is empty");
			throw new UnprocessableException("Country Details not found");
		}
		return countryInfo2;
	}
	

//-------------------get api to view all trailer---------------------------------------
	public List<TrailerDto> getAllTrailers(String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		List<TrailerDto> allTrailer=new ArrayList<>();
		allTrailer=trailerRepo.findAllTrailers();
		if(allTrailer.isEmpty()) {
			logger.info("Trailer database is empty");
			throw new BusinessException("Trailer details not found");
		}
		return allTrailer;
	}

//---------------------------------get all colors----------------------------------------------
	public List<String> getAllInventoryColors(String token) throws JsonMappingException, JsonProcessingException {
		if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		
		List<String> typesOfColor=customConfig.typesOfColor();
		return typesOfColor;
	}

//----------------------------------get user by id-------------------------------------
	public List<CustomUsersResponseDto> getAllUsersById(int id,String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		List<CustomUserDto> listAllUsers=new ArrayList<>();
		 listAllUsers=userRepo.findUserByCustomUserDto();
		 
		 if(listAllUsers.isEmpty()) {
				logger.error("empty list nothing to display no users in database");
				//throw new BusinessException("404","empty list");
				throw new NotFoundExceptions("No users found");
			}
			
			//List<CustomRolesDto> roleList=new ArrayList<>();
			//List<CustomPhoneDto> phoneList=new ArrayList<>();
			List<CustomUsersResponseDto> responseList=new ArrayList<>();
			List<CustomUsersResponseDto> responseList1=new ArrayList<>();
			for(CustomUserDto user:listAllUsers) {
				CustomUsersResponseDto customUsersResponseDto=new CustomUsersResponseDto();
				customUsersResponseDto.setUserId(user.getUserId());
				customUsersResponseDto.setEmail(user.getEmail());
				customUsersResponseDto.setFirstName(user.getFirstName());
				customUsersResponseDto.setLastName(user.getLastName());
				customUsersResponseDto.setStatus(user.getStatus());
				customUsersResponseDto.setLastSeen(user.getLastSeen());
				customUsersResponseDto.setRoleDetails(roleRepo.findAllRolesByUserId(user.getUserId()));
				customUsersResponseDto.setPhoneDetails(phoneRepo.findAllphoneNos(user.getUserId()));
				responseList.add(customUsersResponseDto);
				//System.out.println(phoneRepo.findAllphoneNos(user.getUserId()));
								
				//System.out.println(roleRepo.findAllRolesByUserId(user.getUserId()).get(0).getRoleName());
				
				
			}
			
			for(CustomUsersResponseDto ids:responseList) {
				
				if(ids.getUserId()==id) {
					responseList1.add(ids);
				}
				
				
			}
			if(responseList1.isEmpty()) {
				logger.error("empty list nothing to display no users in database");
				throw new BusinessException("No user with this id found enter correct id");
			}
		 
		 return responseList1;
	}

	//---------------post api to add inventory--------------------------------------
    public ResponseEntity<ResponseModel> addInventory(InvtreDto invtreDto,String token) throws JsonMappingException, JsonProcessingException {
        
         if(validateService.isTokenValid(token))
             throw new UnauthorizedException("Sorry Token is expired please login again");
        
        //checking for null
        if(invtreDto.getGroupcolor()==null)throw new NotFoundExceptions("Group color is missing");
        if(invtreDto.getGroupName()==null)throw new NotFoundExceptions("Group name is missing");
        if(invtreDto.getLeadDriver()==null)throw new NotFoundExceptions("Lead Driver name is missing"); 
        
          try {
            
            Inventory inventory2=new Inventory();
            inventory2.setGroupName(invtreDto.getGroupName());
            inventory2.setGroupcolor(invtreDto.getGroupcolor());
            inventory2.setLeadDriver(invtreDto.getLeadDriver());
            inventory2.setExist(1);
            inventory2.setTractorCount(invtreDto.getTrailers().size());
            if(invtreDto.getComment()!=null)
    			inventory2.setComment(invtreDto.getComment());
         	

            inventoryRepo.save(inventory2); 
            
            for(Trailer trailer:invtreDto.getTrailers()) {
            	Trailer trailer2=trailerRepo.findById(trailer.getTrailerId()).get();
            	
            	trailer2.setTrailerName(trailer.getTrailerName());
            	trailer2.setGroupColor(invtreDto.getGroupcolor());
            	trailer2.setTrailerNo(trailer.getTrailerNo());
            	trailer2.setTractorWeight(trailer.getTractorWeight());
            	trailer2.setSkyBitzNo(trailer.getSkyBitzNo());
            	trailer2.setImage(trailer.getImage());
            	trailer2.setComment(trailer.getComment());
            	trailer2.setTrailerWeight(trailer.getTrailerWeight());
            	trailer2.setTrailertype(trailer.getTrailertype());
            	trailer2.setInventoryId(inventory2.getInventoryId());
            	trailerRepo.save(trailer2);
            
            }    
        
            return restExceptionHandler.responseHandler("inventory Saved");
            
        }catch (Exception e) {
            logger.error(e.getMessage());
            throw new ConflictException("group name is already exist");
        }
        
    }

    
//-----------get api to list all inventory----------------------------------------

	
	public List<Inventory> getAllInventory(String token) throws JsonMappingException, JsonProcessingException {
		if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		
		List<Inventory> inventoryResponse=new ArrayList<>();
		
		inventoryResponse=inventoryRepo.findAllInventory();
		
		
		if(inventoryResponse.isEmpty()) {
			throw new BusinessException("No Inventory found");
		}
		
		return inventoryResponse;
		
	}


  /*//--------------------post api to add trailer---------------------------------
	public ResponseEntity<ResponseModel> addTrailer(Trailer trailer,String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		//if(trailer.getInventoryId()!=0) 
			// trailingInventory(trailer);
		
		Trailer trailer2=new Trailer();
		
		//check if user trying to input null
		//if(trailer.getGroupColor()==null) throw new NotFoundExceptions("Group color is missing");
		if(trailer.getTrailerName()==null) throw new NotFoundExceptions("trailer name is missing");
		if(trailer.getTrailerNo()==null) throw new NotFoundExceptions("trailer number is missing");
		//if(trailer.getSkyBitzNo()==null) throw new NotFoundExceptions("skybitz number is missing");
		if(trailer.getTractorWeight()==0) throw new NotFoundExceptions("tracktor weight is missing");
		if(trailer.getTrailerWeight()==0) throw new NotFoundExceptions("trailer weight is missing");
		if(trailer.getTrailertype()==null) throw new NotFoundExceptions("trailer type is missing");
		
		
		
		//check unique trailer number
		//checking trailer number is unique or not
		int trailerNoUniqueChk=0;
		String trailerNo=trailer.getTrailerNo().toString();
		List<String> trailerNoList=new ArrayList<>();
		trailerNoList=trailerRepo.getAllTrailerNo();
		for (String str : trailerNoList) {
				if(str.equals(trailerNo)) {
					trailerNoUniqueChk+=1;
					break;
				}
		}
						
		if(trailerNoUniqueChk>0) {
				throw new NotFoundExceptions("Enter the unique trailer number");
		}
				
		//checking Trailer Type is correct or not
		int trailerTypChk=0;
		String trailerTyp=trailer.getTrailertype().toString();
		List<String> trailerTypList=new ArrayList<>(Arrays.asList("Gear",
			"Prolyte","Delay","Steel","Poles","V1"));
				
		for (String str : trailerTypList) {
				if(str.equals(trailerTyp)) {
					trailerTypChk+=1;
					break;
				}
		}
=======*/
	
	
//--------------------post api to add trailer---------------------------------
		public ResponseEntity<ResponseModel> addTrailer(Trailer trailer,String token) throws JsonMappingException, JsonProcessingException {
			 if(validateService.isTokenValid(token)) 
				 throw new UnauthorizedException("Sorry Token is expired please login again");
//			if(trailer.getInventoryId()!=0) 
//				 trailingInventory(trailer);
			 
				System.out.println(trailer.getInventoryId());
//				 throw new UnauthorizedException("Sorry Token is expired please login again");
			
			Trailer trailer2=new Trailer();
			
			//check if user trying to input null
			//if(trailer.getGroupColor()==null) throw new NotFoundExceptions("Group color is missing");
			if(trailer.getTrailerName()==null) throw new NotFoundExceptions("trailer name is missing");
			if(trailer.getTrailerNo()==null) throw new NotFoundExceptions("trailer number is missing");
			//if(trailer.getSkyBitzNo()==null) throw new NotFoundExceptions("skybitz number is missing");
			if(trailer.getTractorWeight()==0) throw new NotFoundExceptions("tracktor weight is missing");
			if(trailer.getTrailerWeight()==0) throw new NotFoundExceptions("trailer weight is missing");
			if(trailer.getTrailertype()==null) throw new NotFoundExceptions("trailer type is missing");
			
			//check unique trailer number
			//checking trailer number is unique or not
			int trailerNoUniqueChk=0;
			String trailerNo=trailer.getTrailerNo().toString();
			List<String> trailerNoList=new ArrayList<>();
			trailerNoList=trailerRepo.getAllTrailerNo();
			for (String str : trailerNoList) {
					if(str.equals(trailerNo)) {
						trailerNoUniqueChk+=1;
						break;
					}
			}
							
			if(trailerNoUniqueChk>0) {
					throw new NotFoundExceptions("Enter the unique trailer number");
			}	
			
			//checking Trailer Type is correct or not
			int trailerTypChk=0;
			String trailerTyp=trailer.getTrailertype().toString();
			List<String> trailerTypList=new ArrayList<>(Arrays.asList("Gear",
				"Prolyte","Delay","Steel","Poles","V1"));
					
			for (String str : trailerTypList) {
					if(str.equals(trailerTyp)) {
						trailerTypChk+=1;
						break;
					}
			}
			
		if(trailerTypChk==0) {
			logger.error("Enterd wrong trailer type");
			throw new NotFoundExceptions("Enter the correct trailer type");
		}
				
			
		//checking unique skybitzno
		if(trailer.getSkyBitzNo()!=null) {
			int skyBitzNoUniqueChk=0;
			String skybtz=trailer.getSkyBitzNo().toString();
			
				List<String> skyBtzNoList=new ArrayList<>();
				skyBtzNoList=trailerRepo.getAllSkyBtzNo();
				for (String str : skyBtzNoList) {
					if(str.equals(skybtz)) {
						skyBitzNoUniqueChk+=1;
						break;
					}
				}
			if(skyBitzNoUniqueChk>0) {
					throw new NotFoundExceptions("Enter the unique skybitz number");
			}

		}			
			//increase the tracktor_value by one in inventory
			//inventoryRepo.increaseTracktorCount(trailer.getGroupColor().toString());
					
			
			//save trailer using JPA repo
			try {
				//trailer2.setGroupColor(trailer.getGroupColor());
				trailer2.setTrailerName(trailer.getTrailerName());
				trailer2.setTrailerNo(trailer.getTrailerNo());
				trailer2.setSkyBitzNo(trailer.getSkyBitzNo());
				trailer2.setTractorWeight(trailer.getTractorWeight());
				trailer2.setTrailertype(trailer.getTrailertype());
				trailer2.setTrailerWeight(trailer.getTrailerWeight());
				
				if(trailer.getComment()!=null)
					trailer2.setComment(trailer.getComment());
				
				System.out.println(trailer.getInventoryId());
				if(trailer.getInventoryId()!=0) {
					System.out.println(trailer.getInventoryId());
					Inventory inventory = inventoryRepo.findById(trailer.getInventoryId()).get();
					inventory.setTractorCount(inventory.getTractorCount()+1);
					System.out.println(trailer.getGroupColor());
					trailer2.setGroupColor(inventory.getGroupcolor());
					trailer2.setInventoryId(trailer.getInventoryId());
					
					System.out.println(trailer.getInventoryId());
				}
				System.out.println(trailer.getInventoryId());
				System.out.println("Saving-------------------");
				trailerRepo.save(trailer2);
			}catch (Exception e) {
				logger.error("error when adding trailer");
				throw new NotFoundExceptions(e.toString());
			}
			return restExceptionHandler.responseHandler("trailer Saved");
		}
		


	//------------get Api to view inventory by id----------------------------------------
	public InvtreDto getInventoryById(int id, String token) throws JsonMappingException, JsonProcessingException {
		
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		
		 Inventory inventory=inventoryRepo.findById(id).get();
		 InvtreDto invtreDto=new InvtreDto();
		 
		 invtreDto.setGroupName(inventory.getGroupName());
		 invtreDto.setGroupcolor(inventory.getGroupcolor());
		 invtreDto.setLeadDriver(inventory.getLeadDriver());
		 invtreDto.setComment(inventory.getComment());
		 invtreDto.setTractorCount(inventory.getTractorCount());
		 
		 List<Trailer> trailers=trailerRepo.findTrailerByInventoryId(id);
		 invtreDto.setTrailers(trailers);
		 
		 if((inventory.getInventoryId()==0)||inventory.getExist()==0) {
			 throw new NotFoundExceptions("inventory with given id not found");
		 }
		 
		return invtreDto; 
		 
		
		
	}

//-------------------------put api to add comment to trailer--------------------------------------------------
	public ResponseEntity<ResponseModel> addCommentToTrailer(Trailer trailer,int id,String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		List<Trailer> trailerLst=new ArrayList<>();

		trailerLst=trailerRepo.findAll();
		int commentAdded=0;
		for(Trailer tr:trailerLst) {
			if((tr.getSoftdelete()==0)&&(tr.getTrailerId()==id)) {				
				commentAdded+=1;
				trailerRepo.addComment(trailer.getComment(),id);
			}
		}
		if(commentAdded==0) {
			throw new NotFoundExceptions("enter correct trailer id");
		}
		return restExceptionHandler.responseHandler("comment added");
	}

//-----------------post api to add trailer image----------------------------------
	public ResponseEntity<ResponseModel> AddTrailerImage(String path, MultipartFile file,int id,String token) throws JsonMappingException, JsonProcessingException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		 System.out.println("Put:AddTrailerImage()............................................");
		 
		 
		String name = file.getOriginalFilename();
		
		String filePath = path+ File.separator+name;
		File f = new File(path);
		
		System.out.println(filePath);
		
		System.out.println(f);
		
		if(!f.exists()) {
			f.mkdir();
		}
		
		System.out.println("Add trailer in process");
		
		try {
			System.out.println("in try block:Add trailer in process");
			Files.copy(file.getInputStream(), Paths.get(filePath));
			//trailer.setImage(filePath);		
			//trailerRepo.save(trailer);
			trailerRepo.saveImagePath(filePath,id);
			
			System.out.println("in try block:Add trailer in process");
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new ConflictException("Image is already exist");
		
		}
	
	 return restExceptionHandler.responseHandler("trailer image Saved");
	
	}


	public ResponseEntity<ByteArrayResource> GetLocationImage(int id, String token) throws IOException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		 Location location = locationRepo.findById(id);
		 
		 System.out.println(location.getPhoto());
		 
		 final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
				 location.getPhoto()
	        )));
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .contentLength(inputStream.contentLength())
	                .body(inputStream);
	}
		
	public ResponseEntity<ByteArrayResource> GetTrailerImage(int id, String token) throws IOException {
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		 System.out.println("getTrailerImage()...........................");
		 
		 Trailer trailer = trailerRepo.findTrailerById(id);
		 
		 System.out.println(trailer.getImage());
		 System.out.println("getTrailerImage()...........................2");		 
		 final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
				 trailer.getImage()
	        )));
		 
		 
//		 ByteArrayResource[] fileContent = FileUtils.readFileToByteArray(new File(trailer.getImage()));
//		 String encodedString = Base64.getEncoder().encodeToString(fileContent);
//		 System.out.println(encodedString);
//		 
		 System.out.println("getTrailerImage()...........................3");
	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .contentLength(inputStream.contentLength())
	                .body(inputStream);
	}

	//---------------------put api to update inventory-------------------------------------

	public ResponseEntity<ResponseModel> updateInventory(InvtreDto invtreDto, int id,String token) throws JsonMappingException, JsonProcessingException {
		
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		 	Inventory inventory2 = inventoryRepo.findById(id).get();
		 	
	        //checking for null
	        if(invtreDto.getGroupcolor()==null)throw new NotFoundExceptions("Group color is missing");
	        if(invtreDto.getGroupName()==null)throw new NotFoundExceptions("Group name is missing");
	        if(invtreDto.getLeadDriver()==null)throw new NotFoundExceptions("Lead Driver name is missing"); 
	                
	        
	        try {
	            
	            inventory2.setGroupName(invtreDto.getGroupName());
	            inventory2.setGroupcolor(invtreDto.getGroupcolor());
	            inventory2.setLeadDriver(invtreDto.getLeadDriver());
	            inventory2.setExist(1);
	            inventory2.setTractorCount(invtreDto.getTrailers().size());
	            
	            if(invtreDto.getComment()!=null)
	    			inventory2.setComment(invtreDto.getComment());
	        	
	            inventoryRepo.save(inventory2); 
	            
	           trailerRepo.deleteinventoryIdInTrailer(inventory2.getInventoryId());
	            
	            for(Trailer trailer:invtreDto.getTrailers()) {
	            	Trailer trailer2=trailerRepo.findById(trailer.getTrailerId()).get();
	            	
	            	trailer2.setTrailerName(trailer.getTrailerName());
	            	trailer2.setGroupColor(invtreDto.getGroupcolor());
	            	trailer2.setTrailerNo(trailer.getTrailerNo());
	            	trailer2.setTractorWeight(trailer.getTractorWeight());
	            	trailer2.setSkyBitzNo(trailer.getSkyBitzNo());
	            	trailer2.setImage(trailer.getImage());
	            	trailer2.setComment(trailer.getComment());
	            	trailer2.setTrailerWeight(trailer.getTrailerWeight());
	            	trailer2.setTrailertype(trailer.getTrailertype());
	            	trailer2.setInventoryId(inventory2.getInventoryId());
	            	trailerRepo.save(trailer2);
	            }
	            
	            List<Trailer> trailers = trailerRepo.findTrailerByInventoryId(id);
	            for(Trailer trailer:trailers) {
	            	trailer.setGroupColor(invtreDto.getGroupcolor());
	            	trailerRepo.save(trailer);
	            }
	            	        
	            return restExceptionHandler.responseHandler("inventory Updated Successfully");
	            
	        }catch (Exception e) {
	            logger.error(e.getMessage());
	            throw new NotFoundExceptions(e.getMessage());
	        }
		
	
	}

//--------------delete api to delete inventory-------------------------------------------
	public ResponseEntity<ResponseModel> DeleteInventory(int id,String token) throws JsonMappingException, JsonProcessingException {
		
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		 
		Optional<Inventory> inventory=inventoryRepo.findById(id);
		if((inventory.isEmpty())||(inventory.get().getExist()==0)) {
			throw new NotFoundExceptions("inventory with this id not found");
		}
		
		try {
			//String color=inventoryRepo.findColorById(id);
			
			List<Trailer> trailers=trailerRepo.findTrailerByInventoryId(id);
			
			//soft deleting all trailers related to the inventory
			for(Trailer trailer:trailers) {
				trailer.setGroupColor(null);
				trailer.setInventoryId(0);
				trailerRepo.save(trailer);
			}
			
			//deleting inventory
			//inventoryRepo.deleteById(id);	
			inventoryRepo.softdeleteById(id);
			
			//make tractor count to zero for the deleted inventory
			inventoryRepo.makeTrailerCountZero(id);
			
			return restExceptionHandler.responseHandler("inventory deleted successfully");
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new NotFoundExceptions(e.getMessage());
		}
	}

	//--------------delete api to delete inventory-------------------------------------------
		/*public ResponseEntity<ResponseModel> DeleteInventory(int id,String token) throws JsonMappingException, JsonProcessingException {
			
			 if(validateService.isTokenValid(token)) 
				 throw new UnauthorizedException("Sorry Token is expired please login again");
			 
			Optional<Inventory> inventory=inventoryRepo.findById(id);
			if((inventory.isEmpty())||(inventory.get().getExist()==0)) {
				throw new NotFoundExceptions("inventory with this id not found");
			}
			
			try {
				String color=inventoryRepo.findColorById(id);
				List<Integer> trailerids=trailerRepo.findIdsWithExColor(color);
			
				//deleting all trailers related to the inventory
				for(Integer ids:trailerids) {
					//trailerRepo.deleteById(ids);
					trailerRepo.softdeleteById(ids);
				}
				
				//deleting inventory
				//inventoryRepo.deleteById(id);	
				inventoryRepo.softdeleteById(id);
				
				//make tractor count to zero for the deleted inventory
				inventoryRepo.makeTrailerCountZero(id);
				
				return restExceptionHandler.responseHandler("inventory deleted successfully");
			}catch (Exception e) {
				logger.error(e.getMessage());
				throw new NotFoundExceptions(e.getMessage());
			}
		}*/

	public List<Trailer> getTrailersByColor(String color,String token) throws JsonMappingException, JsonProcessingException {
		
		 if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		
		List<Trailer> trailerList=trailerRepo.findTrailersByColor(color);
		if(trailerList.isEmpty()) {
			logger.info("No trailers Found with given color");
			throw new BusinessException("No trailer found with given color");	
		}
		return trailerList;

	}

////----------------------------delete trailer---------------------------------------------------
	public ResponseEntity<ResponseModel> deleteTrailer(int id,String token) throws JsonMappingException, JsonProcessingException {
		if(validateService.isTokenValid(token)) 
			 throw new UnauthorizedException("Sorry Token is expired please login again");
		
		Optional<Trailer> trailerOptional=trailerRepo.findById(id);
		if((trailerOptional.isEmpty())||(trailerOptional.get().getSoftdelete()==1)) {
			throw new BusinessException("Trailer with this id not found");
		}
		//String color=trailerOptional.get().groupColor;
		try {
			//decrese tractor count of this inventory by one
			//inventoryRepo.decreaseTractorCount(color);
			Trailer trailer=new Trailer();
			trailer=trailerRepo.findById(id).get();
			
			
			if(trailer.getInventoryId()==0) {
				trailer.setSoftdelete(1);
				trailerRepo.save(trailer);
				return restExceptionHandler.responseHandler("Trailer deleted successfully");
			}
			
			int invId=trailer.getInventoryId();
			Inventory inventory=new Inventory();
			inventory=inventoryRepo.findById(invId).get();
			Inventory inventory2=new Inventory();
			
			//update inventory trailer count
			inventory2.setInventoryId(inventory.getInventoryId());
			inventory2.setGroupName(inventory.getGroupName());
			inventory2.setGroupcolor(inventory.getGroupcolor());
			inventory2.setLeadDriver(inventory.getLeadDriver());
			inventory2.setComment(inventory.getComment());
			inventory2.setExist(inventory.getExist());
			inventory2.setTractorCount(inventory.getTractorCount()-1);
			inventoryRepo.save(inventory2);
		
			//delete trailer
			trailer.setGroupColor(null);
			trailer.setInventoryId(0);
			trailer.setSoftdelete(1);
			trailerRepo.save(trailer);
			return restExceptionHandler.responseHandler("Trailer deleted successfully");
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new NotFoundExceptions(e.getMessage());
		}
	}

	
	//----------------------------delete trailer---------------------------------------------------
//		public ResponseEntity<ResponseModel> deleteTrailer(int id,String token) throws JsonMappingException, JsonProcessingException {
//			if(validateService.isTokenValid(token)) 
//				 throw new UnauthorizedException("Sorry Token is expired please login again");
//			
//			Optional<Trailer> trailerOptional=trailerRepo.findById(id);
//			if(trailerOptional.isEmpty()) {
//				throw new BusinessException("Trailer with this id not found");
//			}
//			String color=trailerOptional.get().groupColor;
//			try {
//				//decrese tractor count of this inventory by one
//				inventoryRepo.decreaseTractorCount(color);
//			
//				//delete trailer
//				trailerRepo.deleteById(id);
//				return restExceptionHandler.responseHandler("Trailer deleted successfully");
//			}catch (Exception e) {
//				logger.error(e.getMessage());
//				throw new NotFoundExceptions(e.getMessage());
//			}
//		}


		public ResponseEntity<ResponseModel> CommentInventory(int id, String token, Inventory inventory) throws JsonMappingException, JsonProcessingException {
			if(validateService.isTokenValid(token)) 
				 throw new UnauthorizedException("Sorry Token is expired please login again");
			
			Inventory inventory1 = inventoryRepo.findById(id).get();
			if(inventory.getComment()==null)
				return restExceptionHandler.responseHandler("Comment is not added");
			inventory1.setComment(inventory.getComment());
			inventoryRepo.save(inventory1);
						
			return restExceptionHandler.responseHandler("Trailer Comment added successfully");
		}


		public Object getTrailer(String token, int id) throws JsonMappingException, JsonProcessingException {
			if(validateService.isTokenValid(token)) 
				 throw new UnauthorizedException("Sorry Token is expired please login again");
			
			Trailer trailer = trailerRepo.findTrailerByIds(id);
			if(trailer==null)
				throw new UnprocessableException("trailer doesn't exist");
						
			return trailer;
		}


}



