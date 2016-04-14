package com.journalpublication.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.journalpublication.JournalToken;
import com.journalpublication.domain.Account;
import com.journalpublication.domain.Journal;
import com.journalpublication.domain.Subscription;
import com.journalpublication.services.SubscriberService;

@RestController
public class SubscriptionApiController {

	@Autowired
	private SubscriberService subscriberService;

    @RequestMapping(value = "/api/subscriptions", method = RequestMethod.GET)
    ArrayList<Journal> list() {

//    	JournalToken token = (JournalToken)SecurityContextHolder.getContext().getAuthentication();
//    	Account user = (Account)token.getCredentials();
//    	model.addAttribute("user", user);
    	
    	ArrayList<Journal> journals = new ArrayList<Journal>();

    	Account subscriber = new Account();
    	subscriber.setId(3);
    	ArrayList<Subscription> subscriptions = this.subscriberService.getSubscribedJournalsForSubscriber(subscriber);
    	for (Subscription subscription : subscriptions) {
    		journals.add(this.subscriberService.findJournalById(subscription.getJournalId()));
    	}

        return journals;
    }
}
