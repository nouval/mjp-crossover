package com.journalpublication.repositories;

import org.springframework.data.repository.CrudRepository;
import com.journalpublication.domain.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {

	// findOne by email & type
	Account findOneByEmailAndTypeIgnoreCase(String email, String type);
}
