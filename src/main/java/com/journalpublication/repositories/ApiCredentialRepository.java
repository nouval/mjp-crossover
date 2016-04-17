package com.journalpublication.repositories;

import org.springframework.data.repository.CrudRepository;

import com.journalpublication.domain.ApiCredential;

/**
 * 
 * @author nouval
 *
 */
public interface ApiCredentialRepository extends CrudRepository<ApiCredential, Integer> {

	/**
	 * Returns single ApiCredential by it's api key
	 * @param apiKey
	 * @return
	 */
	ApiCredential findOneByApiKey(String apiKey);
}
