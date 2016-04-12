package com.journalpublication.services;

import java.util.ArrayList;

import com.journalpublication.domain.Account;
import com.journalpublication.domain.Journal;

public interface PublisherService {
	
	ArrayList<Journal> getJournalsForPublisher(Account publisher);
	Journal uploadJournalForPublisher(Journal journal, Account publisher);
	Journal findJournalById(Integer journalId);
}
