package com.journalpublication.controllers;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.journalpublication.JournalToken;
import com.journalpublication.domain.Account;
import com.journalpublication.domain.Journal;
import com.journalpublication.services.PublisherService;

@Controller
public class JournalController {

	@Autowired
	private PublisherService publisherService;
	
	@RequestMapping(value = "/journal", method = RequestMethod.GET)
	String journal(Model model) {

    	JournalToken token = (JournalToken)SecurityContextHolder.getContext().getAuthentication();
    	Account user = (Account)token.getCredentials();
    	model.addAttribute("user", user);
    	
    	Journal journal = new Journal();
    	model.addAttribute("journal", journal);
		
		return "journal";
	}

	@RequestMapping(value = "/journal", method = RequestMethod.POST)
	String journalSave(Journal journal, Model model, @RequestParam("journalfile") MultipartFile file) {

    	JournalToken token = (JournalToken)SecurityContextHolder.getContext().getAuthentication();
    	Account user = (Account)token.getCredentials();
    	model.addAttribute("user", user);
    	
    	if (!file.isEmpty()) {
    		try {
    			
    			journal.setFilename(file.getOriginalFilename());
    			journal.setContent(file.getBytes());
    			String pdfHeader = new String(Arrays.copyOf(journal.getContent(), 5), StandardCharsets.UTF_8);
    			if (!pdfHeader.equals("%PDF-")) {
    				return "journal";
    			}
    		} catch (IOException ex) {
    			return "journal";
    		}
    	}    	
    	this.publisherService.uploadJournalForPublisher(journal, user);

    	return "redirect:journals";
	}
	
    @RequestMapping(value = "/journals", method = RequestMethod.GET)
    String list(Model model){

    	JournalToken token = (JournalToken)SecurityContextHolder.getContext().getAuthentication();
    	Account user = (Account)token.getCredentials();
    	model.addAttribute("user", user);
    	
    	ArrayList<Journal> journals = this.publisherService.getJournalsForPublisher(user);
    	model.addAttribute("journals", journals);

        return "journals";
    }	
}
