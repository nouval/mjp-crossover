package com.journalpublication.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journalpublication.domain.Account;
import com.journalpublication.domain.Journal;
import com.journalpublication.domain.Subscription;
import com.journalpublication.repositories.JournalRepository;
import com.journalpublication.repositories.SubscriptionRepository;

@Service
public class SubscriberServiceImpl implements SubscriberService {
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private JournalRepository journalRepository;
	
	@Override
	public ArrayList<Subscription> getSubscribedJournalsForSubscriber(Account subscriber) {

		return this.subscriptionRepository.findByUserId(subscriber.getId());
	}

	@Override
	public ArrayList<Journal> FindAllJournals() {
		
		// select j.* 
		// from journal j 
		// left join subscription s 
		//   on s.journal_id = j.id 
		// where s.id is null;
		return (ArrayList<Journal>) this.journalRepository.findAll();
	}

	@Override
	public Subscription subscribeJournalForSubscriber(Journal journal, Account subscriber) {

		Subscription subscription = new Subscription();
		subscription.setJournalId(journal.getId());
		subscription.setUserId(subscriber.getId());
		this.subscriptionRepository.save(subscription);
		
		return null;
	}

	@Override
	public ArrayList<Journal> findJournalByCriteria(Integer journalId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Journal findJournalById(Integer journalId) {

		return this.journalRepository.findOne(journalId);
	}

}
