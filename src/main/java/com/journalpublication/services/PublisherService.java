package com.journalpublication.services;

import java.util.ArrayList;

import com.journalpublication.domain.Account;
import com.journalpublication.domain.Journal;

/**
 * 
 * @author nouval
 *
 */
public interface PublisherService {
	
	/**
	 * 
	 * @param publisher
	 * @return
	 */
	ArrayList<Journal> getJournalsForPublisher(Account publisher);
	
	/**
	 * 
	 * @param journal
	 * @param publisher
	 * @return
	 */
	Journal uploadJournalForPublisher(Journal journal, Account publisher);
	
	/**
	 * 
	 * @param journalId
	 * @return
	 */
	Journal findJournalById(Integer journalId);
}
