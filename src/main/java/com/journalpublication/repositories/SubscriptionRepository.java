package com.journalpublication.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.journalpublication.domain.Subscription;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {

	// findOne by email & type
	ArrayList<Subscription> findByUserId(Integer userId);
}
