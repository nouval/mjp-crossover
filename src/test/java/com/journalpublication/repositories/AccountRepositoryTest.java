package com.journalpublication.repositories;


import com.journalpublication.configuration.RepositoryConfiguration;
import com.journalpublication.domain.Account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class AccountRepositoryTest {
	
	private AccountRepository accountRepository;
	
	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	@Test
	public void test_SaveAccount_Success() {
		// setup account
		Account account = new Account();
		account.setEmail("test@email.com");
		account.setFullname("test fullname");
		account.setPassword("password");
		account.setSalt("salt");
		account.setType("pub");
		
		long accountCountBeforeSave = this.accountRepository.count();
		
		// save account, verify has Id
		assertNull(account.getId());
		this.accountRepository.save(account);
		assertNotNull(account.getId());
		
		// fetch from DB
		Account fetchedAccount = this.accountRepository.findOne(account.getId());
		
		// should not be null
		assertNotNull(fetchedAccount);
		
		// should equals
		assertEquals(account.getId(), fetchedAccount.getId());
		assertEquals(account.getFullname(), fetchedAccount.getFullname());
		
		// row count
		long accountCountAfterSave = this.accountRepository.count();
		assertEquals(accountCountAfterSave, accountCountBeforeSave + 1);
	}
}
