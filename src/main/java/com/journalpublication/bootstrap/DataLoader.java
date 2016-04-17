package com.journalpublication.bootstrap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.journalpublication.domain.Account;
import com.journalpublication.domain.ApiCredential;
import com.journalpublication.domain.Journal;
import com.journalpublication.domain.Subscription;
import com.journalpublication.repositories.AccountRepository;
import com.journalpublication.repositories.ApiCredentialRepository;
import com.journalpublication.repositories.JournalRepository;
import com.journalpublication.repositories.SubscriptionRepository;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private JournalRepository journalRepository;
	
	@Autowired
	private ApiCredentialRepository apiCredentialRepository;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	private Logger log = Logger.getLogger(DataLoader.class);
	
	/**
	 * Load sets of data into H2 (in-memory) database, works as data seeding purposes
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		
		Account user = new Account();
		user.setEmail("nouval@gmail.com");
		user.setFullname("Nouval Hussein");
		user.setPassword("3+NpwuAGSiQ819ktICdaS7gd3bw5UCpMLw/SldT6Fbk=");
		user.setSalt("fUCD/2CjEBIGKMi3bOoJtEbtTLc=");
		user.setType("pub");

		this.accountRepository.save(user);

		log.info("Saved User - id: " + user.getId());
		
		Journal journal = new Journal();
		journal.setFilename("filename-1.1");
		journal.setSubject("subject-1.1");
		journal.setTags("tags...-1.1");
		journal.setUserId(user.getId());
		
		this.journalRepository.save(journal);
		
		journal = new Journal();
		journal.setFilename("filename-1.2");
		journal.setSubject("subject-1.2");
		journal.setTags("tags...-1.2");
		journal.setUserId(user.getId());
		
		this.journalRepository.save(journal);

		user = new Account();
		user.setEmail("pub@gmail.com");
		user.setFullname("Pub Hussein");
		user.setPassword("3+NpwuAGSiQ819ktICdaS7gd3bw5UCpMLw/SldT6Fbk=");
		user.setSalt("fUCD/2CjEBIGKMi3bOoJtEbtTLc=");
		user.setType("pub");

		this.accountRepository.save(user);

		log.info("Saved User - id: " + user.getId());

		journal = new Journal();
		journal.setFilename("filename-2.1");
		journal.setSubject("subject-2.1");
		journal.setTags("tags...-2.1");
		journal.setUserId(user.getId());

		this.journalRepository.save(journal);

		user = new Account();
		user.setEmail("sub@gmail.com");
		user.setFullname("Sub Hussein");
		user.setPassword("3+NpwuAGSiQ819ktICdaS7gd3bw5UCpMLw/SldT6Fbk=");
		user.setSalt("fUCD/2CjEBIGKMi3bOoJtEbtTLc=");
		user.setType("sub");

		this.accountRepository.save(user);

		log.info("Saved User - id: " + user.getId());

		Subscription subscription = new Subscription();
		subscription.setJournalId(journal.getId());
		subscription.setUserId(user.getId());

		this.subscriptionRepository.save(subscription);

		ApiCredential apiCred = new ApiCredential();
		apiCred.setApiKey("N2ZkNjQwY2ItZGE4ZC00NjBmLThjODQtMzhkZjMyM2Q2YTFl");
		apiCred.setAccountType("sub");

		this.apiCredentialRepository.save(apiCred);
	}
}
