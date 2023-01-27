package com.costrategix.gbp.capstone2.actuator.Controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.costrategix.gbp.capstone2.actuator.DTO.InvtreDto;
import com.costrategix.gbp.capstone2.actuator.DTO.UserDto;
import com.costrategix.gbp.capstone2.actuator.exception.ResponseModel;
import com.costrategix.gbp.capstone2.actuator.model.Inventory;
import com.costrategix.gbp.capstone2.actuator.model.Location;
import com.costrategix.gbp.capstone2.actuator.model.Trailer;
import com.costrategix.gbp.capstone2.actuator.service.UserService;
import com.costrategix.gbp.capstone2.actuator.service.ValidatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;




@RestController
@CrossOrigin
public class UserController {

	private static final Logger logger=Logger.getLogger(UserController.class);

	 @Autowired
	 private UserService userService;
	 

	 @Autowired
	 private ValidatorService validatorService;
	 
	 
	 
	 @Value("${project.locationimage}")
	 private String locationpath;
	 

//	 @Value("$project.image2")
//	 private String path2;	

	 
	 @Value("${project.trailerimage}")
	 private String inventorypath;    
		

	 


	  @GetMapping("/home")
	  public String home() {
		  logger.info("home():control in the root url");
		  return "welcome home";

	  }
	  
	  @PostMapping("/login")
	  @ResponseBody
	  public ResponseEntity<ResponseModel> Login(@RequestBody UserDto userDto) throws JsonMappingException, JsonProcessingException  {
		  logger.info("home():control in the root url");
		  return userService.CheckCredentials(userDto);
	  }

	   
	  
	  @PostMapping(value="/User")
	  @ResponseBody
	  public ResponseEntity<ResponseModel> InviteNewUser(@RequestHeader("Authorization") String token,@RequestBody UserDto userDto) throws JsonMappingException, JsonProcessingException  {
		  return userService.InviteNewUser(userDto,token);
	  }
	  
	  @PutMapping("/User/{id}")
	  @ResponseBody
	  public ResponseEntity<ResponseModel> UpdateUser(@RequestHeader("Authorization") String token,@PathVariable int id,@RequestBody UserDto userDto) throws JsonMappingException, JsonProcessingException {
		  return userService.UpdateUser(userDto,id,token);
	  }
	  
	  
	  @PostMapping("/RegisterUser")
	  @ResponseBody
	  public ResponseEntity<ResponseModel> RegisterUser(@RequestBody UserDto userDto) throws JsonMappingException, JsonProcessingException {
		  return userService.RegisterUser(userDto);
	  }
	  
	  @RequestMapping(value = "/location", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	  public ResponseEntity<ResponseModel> AddLocation(@RequestHeader("Authorization") String token,@RequestPart(value = "image", required = false) MultipartFile file, @RequestPart("json") Location location) throws IOException {
			  return userService.AddLocation(location,locationpath,file,token);
	  }
	  
	  //------------put api to update location------------------------------------
	  @RequestMapping(value = "/location/{id}", method = RequestMethod.PUT, consumes = {"multipart/form-data"})
	  public ResponseEntity<ResponseModel> UpdateLocation(@RequestHeader("Authorization") String token,@RequestPart(value = "image", required = false) MultipartFile file, @PathVariable("id") int id, @RequestPart(value = "json", required = false) Location location) throws IOException {
		  return userService.UpdateLocation(location,locationpath,id,file,token);
	  }
	  
	  //------------GEt Location Image By ID------------------------------------
	  @RequestMapping(value = "/locationimage/{id}", method = RequestMethod.GET, produces = {"multipart/form-data"})
	  public ResponseEntity<ByteArrayResource> GetLocationImage(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws IOException {
		  return userService.GetLocationImage(id,token);
	  }
	  
	  @PostMapping("/resendinvite")
	  @ResponseBody
	  public ResponseEntity<ResponseModel> ResendInvite(@RequestHeader("Authorization") String token,@RequestBody UserDto userDto) throws JsonMappingException, JsonProcessingException {
		  return userService.ResendInvite(userDto,token);
	  }
	  
	  
	  @PutMapping("/resendpasswordlink")
	  @ResponseBody
	  public ResponseEntity<ResponseModel> ResendPasswordLink(@RequestBody UserDto userDto) {
		  return userService.ResendPasswordLink(userDto);
	  }
	  
	  @PutMapping("/setresendpassword")
	  @ResponseBody
	  public ResponseEntity<ResponseModel> AcceptResentPasswordFromMail(@RequestHeader("Authorization") String token,@RequestBody UserDto userDto){
		  return userService.AcceptResentPasswordFromMail(userDto,token);
	  }
	  
	  
//	//-------------------post api to add trailer-----------------------------------------
//	   @RequestMapping(value = "/trailer", method = RequestMethod.POST, consumes = {"multipart/form-data"})
//	   public ResponseEntity<ResponseModel> AddTrailer(@RequestHeader("Authorization") String token,@RequestPart("image") MultipartFile file, @RequestPart("json") Trailer trailer) throws IOException {			
//			  return userService.AddTrailer(trailer,inventorypath,file,token);
//	   }

	   
	//---------------new post api to add trailer--------------------------------------------
	   @PostMapping(value = "/trailer")
	   public ResponseEntity<ResponseModel> addTrailer(@RequestHeader("Authorization") String token,@RequestBody Trailer trailer) throws JsonMappingException, JsonProcessingException{
		   return userService.addTrailer(trailer,token);
	   }
	   
	 //--------------new post api to add comment-------------------------------------------
	   @PutMapping(value="/trailercomment/{id}")
	   public ResponseEntity<ResponseModel> addCommentToTrailer(@RequestHeader("Authorization") String token, @RequestBody Trailer trailer,@PathVariable int id) throws JsonMappingException, JsonProcessingException{
		   return userService.addCommentToTrailer(trailer,id,token);
	   }
	   
	 //---------------new Post api to add image to trailer-------------------------------
	   @RequestMapping(value = "/trailerimage/{id}", method = RequestMethod.PUT,consumes = {"multipart/form-data"})
	   public ResponseEntity<ResponseModel> AddTrailerImage(@RequestHeader("Authorization") String token, @RequestPart("image") MultipartFile file,@PathVariable int id) throws IOException {			
			  return userService.AddTrailerImage(inventorypath,file,id,token);
	   }
	 
	   //------------------get api to view all trailer---------------------------------------
	   @GetMapping(value = "/trailers", produces=MediaType.APPLICATION_JSON_VALUE)
		  public ResponseEntity<Object> getAllTrailer(@RequestHeader("Authorization") String token) throws JsonMappingException, JsonProcessingException {
			  return new ResponseEntity<> (userService.getAllTrailers(token),HttpStatus.OK);
	   }  
	   
	   //------------------get api to view all trailer---------------------------------------
	   @GetMapping(value = "/trailer/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
		  public ResponseEntity<Object> getTrailer(@RequestHeader("Authorization") String token,@PathVariable int id) throws JsonMappingException, JsonProcessingException {
			  return new ResponseEntity<> (userService.getTrailer(token,id),HttpStatus.OK);
	   }  
	   
     //-------------------Delete api to delete trailer image-----------------------------------------
	   @RequestMapping(value = "/trailerimage/{id}", method = RequestMethod.DELETE)
	   public ResponseEntity<ResponseModel> DeleteTrailerImage(@RequestHeader("Authorization") String token,@PathVariable("id") int id) throws IOException {			
			  return userService.DeleteTrailerImage(id,token);
	   }
	   
	 //-------------------Update api to update trailer info-----------------------------------------   
	   @RequestMapping(value = "/trailer/{id}", method = RequestMethod.PUT)
	   public ResponseEntity<ResponseModel> UpdateTrailer(@RequestHeader("Authorization") String token,@RequestBody Trailer trailer,@PathVariable("id") int id) throws IOException {			
			  return userService.UpdateTrailer(trailer,id,token);
	   }
	   


	 //---------------Delete api to delete trailer info-----------------------------------------------
	   @RequestMapping(value = "/trailer/{id}", method = RequestMethod.DELETE)
	   public ResponseEntity<ResponseModel> DeleteTrailer(@RequestHeader("Authorization") String token,@PathVariable("id") int id) throws IOException {			
			  return userService.deleteTrailer(id,token);
	  } 
	   

	   
//     //-------------------Update api to update trailer image-----------------------------------------   
//	   @RequestMapping(value = "/trailerimage/{id}", method = RequestMethod.PATCH, consumes = {"multipart/form-data"})
//	   public ResponseEntity<ResponseModel> UpdateTrailerImage(@RequestHeader("Authorization") String token,@RequestPart(value = "image", required = false) MultipartFile file, @PathVariable("id") int id) throws IOException {			
//			  return userService.UpdateTrailerImage(file,inventorypath,id,token);
//	   }

	 //-----------------get api to get trailers by color----------------------------
	  @GetMapping(value="/trailersbycolor/{color}") 
	  public ResponseEntity<Object> getTrailersByColor(@RequestHeader("Authorization") String token,@PathVariable String color) throws JsonMappingException, JsonProcessingException {
		  return new ResponseEntity<> (userService.getTrailersByColor(color,token),HttpStatus.OK);
      }
	  
	  //------------GEt Trailer Image By ID------------------------------------
	  @RequestMapping(value = "/trailerimage/{id}", method = RequestMethod.GET, produces = {"multipart/form-data"})
	  public ResponseEntity<ByteArrayResource> GetTrailerImage(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws IOException {
		  return userService.GetTrailerImage(id,token);
	  }
	  
	  //--------------------------Get APIs to see all users---------------------------------------------
	  @GetMapping(value = "/users", produces=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Object> getAllUsers(@RequestHeader("Authorization") String token) throws JsonMappingException, JsonProcessingException {
		  return new ResponseEntity<> (userService.getAllUsers(token),HttpStatus.OK);
	  }
	  
	  //----------------------Get API to find user by id------------------------------------------
	  @GetMapping(value = "/user/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Object> getAllUsersById(@RequestHeader("Authorization") String token,@PathVariable int id) throws JsonMappingException, JsonProcessingException {
		  return new ResponseEntity<> (userService.getAllUsersById(id,token),HttpStatus.OK);
	  }
	  
	  //------------------------Get api to filter users by status------------------------
	  @GetMapping(value = "/usersfilterstatus/{statusid}", produces=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Object> getFilterAllUsersByStatus(@RequestHeader("Authorization") String token,@PathVariable int statusid) throws JsonMappingException, JsonProcessingException {
		  return new ResponseEntity<> (userService.filterAllUsersByStatus(statusid,token),HttpStatus.OK);
	  }
	  
	  //---------------------Get api to filter users by role----------------------
	  @GetMapping(value = "/usersfilterrole/{roleid}", produces=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Object> getFilterAllUsersByRole(@RequestHeader("Authorization") String token,@PathVariable int roleid) throws JsonMappingException, JsonProcessingException {
		  return new ResponseEntity<> (userService.filterAllUsersByRole(roleid,token),HttpStatus.OK);
	  }
	  	
	  //--------------------Get api to validate invite link----------------------------
	   @GetMapping(value = "/useremail/{email}",produces = MediaType.APPLICATION_JSON_VALUE)
	   public ResponseEntity<ResponseModel> checkIfInviteLinkIsValid(@PathVariable String email) throws JsonMappingException, JsonProcessingException{
		   return validatorService.checkValidity(email);
	   }
	
	   //-------------------Get api to view all locations-------------------------------------
	   @GetMapping(value="/locations")
	   public ResponseEntity<Object> getAllLocationDetaild(@RequestHeader("Authorization") String token) throws JsonMappingException, JsonProcessingException{
		   return new ResponseEntity<>(userService.getAllLocationInfo(token),HttpStatus.OK);
	   }
	   
	   
	   //------------------Get api to view location info by id--------------------------------
	   @GetMapping(value = "/location/{id}")
	   public ResponseEntity<Object> getAllLocationDetailsById(@RequestHeader("Authorization") String token,@PathVariable int id) throws IOException{
		   return new ResponseEntity<>(userService.getAllLocationInfoById(id,token),HttpStatus.OK);
	   }
	   
	   
	   
	   //------------------Get api to view all location types------------------------------------
	   @GetMapping(value = "/locationtypes")
	   public ResponseEntity<Object> getLocationTypes(@RequestHeader("Authorization") String token) throws JsonMappingException, JsonProcessingException{
		   return new ResponseEntity<>(userService.getLocationTypes(token),HttpStatus.OK);
	   }

	   
	   @GetMapping(value="/country/zip/{zip}")
	   public ResponseEntity<Object> getCountryInfoByZip(@RequestHeader("Authorization") String token,@PathVariable String zip) throws JsonMappingException, JsonProcessingException{
		   return new ResponseEntity<>(userService.getCountryDetailsByZip(zip,token),HttpStatus.OK);
	   }
	   
	   //----------------Get api to view all states info by country----------------------------------
	   @GetMapping(value="/country/{country}")
	   public ResponseEntity<Object> getCountryInfo(@RequestHeader("Authorization") String token,@PathVariable String country) throws JsonMappingException, JsonProcessingException{
		   return new ResponseEntity<>(userService.getCountryDetails(country,token),HttpStatus.OK);
	   }
	   

	   //-----------------Get api to view list of inventory colors-------------------------------------------------
	   @GetMapping(value="/inventorycolors")
	   public ResponseEntity<Object> getListOfInventoryColor(@RequestHeader("Authorization") String token) throws JsonMappingException, JsonProcessingException{
		   return new ResponseEntity<>(userService.getAllInventoryColors(token),HttpStatus.OK);
	   }
	   
	   //-----------------Post api to add inventory----------------------------------------------
	   @PostMapping(value = "/inventory")
	   public ResponseEntity<ResponseModel> addInventory(@RequestHeader("Authorization") String token,@RequestBody InvtreDto inventory) throws JsonMappingException, JsonProcessingException{
		   return userService.addInventory(inventory,token);
	   }
	   
	   
	   //-----------------get api to view all inventory---------------------------------------------
	   @GetMapping(value="/inventories")
	   public ResponseEntity<Object> getListOfInventory(@RequestHeader("Authorization") String token) throws JsonMappingException, JsonProcessingException{
		   return new ResponseEntity<>(userService.getAllInventory(token),HttpStatus.OK);
	   }
	   
	   //-----------------get api to view inventory by id---------------------------------------------
	   @GetMapping(value="/inventory/{id}")
	   public ResponseEntity<Object> getInventoryById(@RequestHeader("Authorization") String token,@PathVariable int id) throws JsonMappingException, JsonProcessingException{
		   return new ResponseEntity<>(userService.getInventoryById(id,token),HttpStatus.OK);
	   }
	   
	   //----------------put api to update inventory-----------------------------------------------
	   @PutMapping(value = "/inventory/{id}")
	   public ResponseEntity<ResponseModel> updateInventory(@RequestHeader("Authorization") String token,@RequestBody InvtreDto inventory,@PathVariable int id) throws JsonMappingException, JsonProcessingException{
		   return userService.updateInventory(inventory,id,token);
	   }
	  
	  //----------------delete api to delete inventory-----------------------------------
	  @RequestMapping(value = "/inventory/{id}", method = RequestMethod.DELETE)
	   public ResponseEntity<ResponseModel> DeleteInventory(@RequestHeader("Authorization") String token,@PathVariable("id") int id) throws IOException {			
			  return userService.DeleteInventory(id,token);
	  }
	  
	  //----------------delete api to delete inventory-----------------------------------
	  @RequestMapping(value = "/inventorycomment/{id}", method = RequestMethod.PUT)
	   public ResponseEntity<ResponseModel> CommentInventory(@RequestHeader("Authorization") String token,@PathVariable("id") int id,@RequestBody Inventory inventory) throws IOException {			
			  return userService.CommentInventory(id,token,inventory);
	  }	  


}
