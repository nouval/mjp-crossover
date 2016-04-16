package com.journalpublication.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.journalpublication.Utils;
import com.journalpublication.domain.Account;
import com.journalpublication.domain.Journal;
import com.journalpublication.domain.Subscription;
import com.journalpublication.model.ApiToken;
import com.journalpublication.model.JournalContent;
import com.journalpublication.services.ProfileService;
import com.journalpublication.services.SubscriberService;

@RestController
public class ApiController {

	@Autowired
	private SubscriberService subscriberService;

	@Autowired
	private ProfileService profileService;
	
	public static int API_TOKEN_EXPIRY_IN_MINUTES = 10;
	public static String API_SECRET_KEY_TEMP = "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdC4gSW4gcXVpcy4=";

    @RequestMapping(value={"/api/login"}, method = RequestMethod.POST)
    ApiToken login(@RequestParam(value="username") String username,
    		@RequestParam(value="password") String password,
    		@RequestParam(value="apiKey") String apiKey) {
    
    	ApiToken apitoken = new ApiToken(username, null);
    	
    	Account accountValid = this.profileService.authenticateApi(username, password, apiKey);

    	// TODO: use dynamic key to sign JWT
    	if (accountValid != null) {
    		Calendar date = Calendar.getInstance();
    		date.add(Calendar.MINUTE, API_TOKEN_EXPIRY_IN_MINUTES);

    		String jwt = io.jsonwebtoken.Jwts.builder()
    				.setSubject(username)
    				.setExpiration(date.getTime())
    				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, API_SECRET_KEY_TEMP)
    				.compact();
    		apitoken.setUsername(username);
    		apitoken.setToken(jwt);
    	}

    	return apitoken;
    }

    @RequestMapping(value = "/api/validate", method = RequestMethod.GET)
    Account validateToken(HttpServletRequest request, HttpServletResponse response) {
    	
    	Account subscriber = new Account();
    	String jwToken = request.getHeader("X-AUTH-TOKEN");
    	
    	if (!this.validateJWT(jwToken, subscriber)) {
    		try {
				response.sendError(401);
			} catch (IOException e) {

				e.printStackTrace();
			}
    	}    	

    	return subscriber;
    }

    @RequestMapping(value = "/api/journal/{journalId}", method = RequestMethod.GET)
    JournalContent getJournalViaAPI(@PathVariable Integer journalId, 
    		HttpServletRequest request, HttpServletResponse response) {
    	
    	JournalContent journalWithContent = new JournalContent();
    	Account subscriber = new Account();
    	String jwToken = request.getHeader("X-AUTH-TOKEN");

    	if (!this.validateJWT(jwToken, subscriber)) {
    		try {
				response.sendError(401);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	} else {
    		
    		Journal journal = this.subscriberService.findJournalById(journalId);
    		journalWithContent.setFilename(journal.getFilename());
    		// journalWithContent.setContent(journal.getContent());
    		try {
				journalWithContent.setContent(Utils.convertPdfToImage(journal.getContent()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

    	return journalWithContent;
    }
    
    @RequestMapping(value = "/api/subscriptions", method = RequestMethod.GET)
    ArrayList<Journal> list(HttpServletRequest request, HttpServletResponse response) {
    	
    	ArrayList<Journal> journals = new ArrayList<Journal>();
    	Account subscriber = new Account();
    	String jwToken = request.getHeader("X-AUTH-TOKEN");

    	if (!this.validateJWT(jwToken, subscriber)) {
    		try {
				response.sendError(401);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	} else {
    	
	    	ArrayList<Subscription> subscriptions = this.subscriberService.getSubscribedJournalsForSubscriber(subscriber);
	    	for (Subscription subscription : subscriptions) {
	    		journals.add(this.subscriberService.findJournalById(subscription.getJournalId()));
	    	}
    	}

        return journals;
    }

    private boolean validateJWT(String token, Account subscriber) {

    	// TODO: this is a hack!! should use Spring Security Filter instead!!
    	// use private key instead of shared one N2ZkNjQwY2ItZGE4ZC00NjBmLThjODQtMzhkZjMyM2Q2YTFl
    	boolean isAuthenticated = token != null;    	

		try {
			String username = io.jsonwebtoken.Jwts.parser()
	                .setSigningKey(API_SECRET_KEY_TEMP)
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();

			subscriber.setEmail(username);
			subscriber.setType("sub");

		} catch (Exception e) {
			e.printStackTrace();
			isAuthenticated = false;
		}
		
		return isAuthenticated;
    }
}
