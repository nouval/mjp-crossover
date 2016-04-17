package com.journalpublication.repositories;

import org.springframework.data.repository.CrudRepository;
import com.journalpublication.domain.Account;

/**
 * 
 * @author nouval
 *
 */
public interface AccountRepository extends CrudRepository<Account, Integer> {

	/**
	 * Return single account by it's email and type
	 *  
	 * @param email
	 * @param type
	 * @return
	 */
	Account findOneByEmailAndTypeIgnoreCase(String email, String type);
}
