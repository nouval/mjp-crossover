package com.journalpublication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.journalpublication.JournalToken;
import com.journalpublication.domain.Account;
import com.journalpublication.services.ProfileService;

@Controller
public class IndexController {

	private ProfileService profileService;

	@Autowired
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

    @RequestMapping(value={"/", "/index"}, method = RequestMethod.GET)
    String index() {
        return "index";
    }

    @RequestMapping(value={"/register"}, method = RequestMethod.POST)
    String register(Account account) {

    	Account accountNew = this.profileService.register(account);
    	
    	if (accountNew != null) {
    		return this.setAuthenticationAndRenderView(accountNew);
    	}

    	return "index";
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.POST)
    String login(Account account) {

    	Account accountValid = this.profileService.authenticate(account.getEmail(), account.getPassword(), account.getType());
    	
    	if (accountValid != null) {
    		return this.setAuthenticationAndRenderView(accountValid);
    	}

    	return "index";
    }
    
    @RequestMapping(value={"/logout"}, method = RequestMethod.GET)
    String logout() {
    	
    	JournalToken token = (JournalToken)SecurityContextHolder.getContext().getAuthentication();
    	token.setAuthenticated(false);

    	return "index";
    }
    
    private String setAuthenticationAndRenderView(Account account) {

    	try {
    		JournalToken token = new JournalToken(account.getEmail(), account);	    	
    		SecurityContextHolder.getContext().setAuthentication(token);

    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	return account.getType() == "pub" ? "redirect:journals" : "redirect:subscriptions";
    }
}