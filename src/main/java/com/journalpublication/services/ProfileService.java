package com.journalpublication.services;

import com.journalpublication.domain.Account;

/**
 * Service contract for profile related activity
 * 
 * @author nouval
 *
 */
public interface ProfileService {
	
	/**
	 * 
	 * @param account
	 * @return
	 */
	Account register(Account account);
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @param type
	 * @return
	 */
	Account authenticate(String email, String password, String type);
	
	/**
	 * 
	 * @param email
	 * @param password
	 * @param apiKey
	 * @return
	 */
	Account authenticateApi(String email, String password, String apiKey);
}
