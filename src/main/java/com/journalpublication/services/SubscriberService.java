package com.journalpublication.services;

import java.util.ArrayList;

import com.journalpublication.domain.Account;
import com.journalpublication.domain.Journal;
import com.journalpublication.domain.Subscription;

/**
 * 
 * @author nouval
 *
 */
public interface SubscriberService {
	
	/**
	 * 
	 * @param subscriber
	 * @return
	 */
	ArrayList<Subscription> getSubscribedJournalsForSubscriber(Account subscriber);
	
	/**
	 * 
	 * @return
	 */
	ArrayList<Journal> FindAllJournals();
	
	/**
	 * 
	 * @param journal
	 * @param subscriber
	 * @return
	 */
	Subscription subscribeJournalForSubscriber(Journal journal, Account subscriber);
	
	/**
	 * 
	 * @param journalId
	 * @return
	 */
	ArrayList<Journal> findJournalByCriteria(Integer journalId);
	
	/**
	 * 
	 * @param journalId
	 * @return
	 */
	Journal findJournalById(Integer journalId);
}
