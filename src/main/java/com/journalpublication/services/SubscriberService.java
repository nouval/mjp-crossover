package com.journalpublication.services;

import java.util.ArrayList;

import com.journalpublication.domain.Account;
import com.journalpublication.domain.Journal;
import com.journalpublication.domain.Subscription;

public interface SubscriberService {
	
	ArrayList<Subscription> getSubscribedJournalsForSubscriber(Account subscriber);
	ArrayList<Journal> FindAllJournals();
	Subscription subscribeJournalForSubscriber(Journal journal, Account subscriber);
	ArrayList<Journal> findJournalByCriteria(Integer journalId);
	Journal findJournalById(Integer journalId);
}
