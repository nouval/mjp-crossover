package com.journalpublication.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.journalpublication.domain.Subscription;

/**
 * 
 * @author nouval
 *
 */
public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {

	/**
	 * Returns array of Subscribed journals by specific user's id 
	 * @param userId
	 * @return
	 */
	ArrayList<Subscription> findByUserId(Integer userId);
}
