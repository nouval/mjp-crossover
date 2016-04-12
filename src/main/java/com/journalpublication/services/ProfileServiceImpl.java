package com.journalpublication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journalpublication.Password;
import com.journalpublication.domain.Account;
import com.journalpublication.repositories.AccountRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	private AccountRepository accountRepository;
	
	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	@Override
	public Account register(Account account) {

		try {

	        Password pwd = new Password();
	        account.setSalt(pwd.getSalt());
	        account.setPassword(pwd.hashPasswordAsBase64(account.getPassword()));

			this.accountRepository.save(account);

		} catch (Exception ex) {
			return null;
		}

		return account;
	}

	@Override
	public Account authenticate(String email, String password, String type) {

		Account account = this.accountRepository.findOneByEmailAndTypeIgnoreCase(email, type);
		Password passwd = new Password(account.getSalt());

		return passwd.validatePassword(account.getPassword(), password) ? account : null;
	}
}
