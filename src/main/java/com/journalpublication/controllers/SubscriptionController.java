package com.journalpublication.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.journalpublication.JournalToken;
import com.journalpublication.domain.Account;
import com.journalpublication.domain.Journal;
import com.journalpublication.services.SubscriberService;

@Controller
public class SubscriptionController {

	@Autowired
	private SubscriberService subscriberService;

	@RequestMapping(value = "/subscribe", method = RequestMethod.POST)
	String subscribe(Model model, @RequestParam("journalId") Integer journalId) {

    	JournalToken token = (JournalToken)SecurityContextHolder.getContext().getAuthentication();
    	Account user = (Account)token.getCredentials();
    	model.addAttribute("user", user);

    	Journal journal = new Journal();
    	journal.setId(journalId);

		this.subscriberService.subscribeJournalForSubscriber(journal, user);

		return "redirect:subscriptions";
	}

    @RequestMapping(value = "/subscriptions", method = RequestMethod.GET)
    String list(Model model) {

    	JournalToken token = (JournalToken)SecurityContextHolder.getContext().getAuthentication();
    	Account user = (Account)token.getCredentials();
    	model.addAttribute("user", user);

    	ArrayList<Journal> journals = this.subscriberService.FindAllJournals(); 
    	model.addAttribute("journals", journals);

        return "subscriptions";
    }
}
