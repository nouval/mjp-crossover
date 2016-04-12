package com.journalpublication.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journalpublication.domain.Account;
import com.journalpublication.domain.Journal;
import com.journalpublication.repositories.JournalRepository;

@Service
public class PublisherServiceImpl implements PublisherService {

	@Autowired
	private JournalRepository journalRepository;
	
	@Override
	public ArrayList<Journal> getJournalsForPublisher(Account publisher) {
		
		return this.journalRepository.findByUserId(publisher.getId());
	}

	@Override
	public Journal uploadJournalForPublisher(Journal journal, Account publisher) {
		
		journal.setUserId(publisher.getId());
		Journal journalNew = this.journalRepository.save(journal);

		return journalNew;
	}

	@Override
	public Journal findJournalById(Integer journalId) {

		return this.journalRepository.findOne(journalId);
	}

}
