package com.journalpublication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journalpublication.Password;
import com.journalpublication.domain.Account;
import com.journalpublication.domain.ApiCredential;
import com.journalpublication.repositories.AccountRepository;
import com.journalpublication.repositories.ApiCredentialRepository;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ApiCredentialRepository apiCredentialRepository; 
	
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

	@Override
	public Account authenticateApi(String email, String password, String apiKey) {
		
		try {
			// find type, via apiKey
			ApiCredential apiCred = apiCredentialRepository.findOneByApiKey(apiKey);
			
			if (apiCred != null) {

				return this.authenticate(email, password, apiCred.getAccountType());
			}			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
}
