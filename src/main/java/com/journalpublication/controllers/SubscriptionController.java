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
	
//	@RequestMapping(value = "/subscription", method = RequestMethod.GET)
//	String journal(Model model) {
//
//    	JournalToken token = (JournalToken)SecurityContextHolder.getContext().getAuthentication();
//    	Account user = (Account)token.getCredentials();
//    	model.addAttribute("user", user);
//    	
//    	Journal journal = new Journal();
//    	model.addAttribute("journal", journal);
//		
//		return "journal";
//	}
//
//	@RequestMapping(value = "/journal", method = RequestMethod.POST)
//	String journalSave(Journal journal, Model model, @RequestParam("journalfile") MultipartFile file) {
//
//    	JournalToken token = (JournalToken)SecurityContextHolder.getContext().getAuthentication();
//    	Account user = (Account)token.getCredentials();
//    	model.addAttribute("user", user);
//    	
//    	if (!file.isEmpty()) {
//    		try {
//    			
//    			journal.setFilename(file.getOriginalFilename());
//    			journal.setContent(file.getBytes());
//    			String pdfHeader = new String(Arrays.copyOf(journal.getContent(), 5), StandardCharsets.UTF_8);
//    			if (!pdfHeader.equals("%PDF-")) {
//    				return "journal";
//    			}
//    		} catch (IOException ex) {
//    			return "journal";
//    		}
//    	}    	
//    	this.publisherService.uploadJournalForPublisher(journal, user);
//
//    	return "redirect:journals";
//	}
	
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
