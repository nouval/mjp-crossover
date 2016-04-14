package com.journalpublication.repositories;

import org.springframework.data.repository.CrudRepository;

import com.journalpublication.domain.ApiCredential;

public interface ApiCredentialRepository extends CrudRepository<ApiCredential, Integer> {

	// findOne by email & type
	ApiCredential findOneByApiKey(String apiKey);
}
