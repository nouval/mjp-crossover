package com.journalpublication.controllers;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.journalpublication.domain.Account;
import com.journalpublication.model.ApiToken;
//import com.journalpublication.JournalToken;
//import com.journalpublication.domain.Account;
import com.journalpublication.services.ProfileService;

@RestController
public class LoginApiController {

	private ProfileService profileService;	
	public static String API_KEY_TEMP = "7fd640cb-da8d-460f-8c84-38df323d6a1e";
	public static int API_TOKEN_EXPIRY_IN_MINUTES = 2;

	@Autowired
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

    @RequestMapping(value={"/api/login"}, method = RequestMethod.POST)
    ApiToken login(@RequestParam(value="username") String username,
    		@RequestParam(value="password") String password,
    		@RequestParam(value="apiKey") String apiKey) {
    
    	ApiToken apitoken = null;
    	
    	Account accountValid = this.profileService.authenticate(username, password, "sub");
    	
    	if (accountValid != null && apiKey.equals(LoginApiController.API_KEY_TEMP)) {
    		Calendar date = Calendar.getInstance();
    		date.add(Calendar.MINUTE, LoginApiController.API_TOKEN_EXPIRY_IN_MINUTES);
    		apitoken = new ApiToken(username, LoginApiController.API_KEY_TEMP, date.getTime());    		
    	} else {
    		apitoken = new ApiToken(username, null, null);
    	}

    	return apitoken;
    }
}