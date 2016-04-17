package com.journalpublication.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.journalpublication.domain.Journal;

/**
 * 
 * @author nouval
 *
 */
public interface JournalRepository extends CrudRepository<Journal, Integer> {

	/**
	 * Return sets of Journal, by user id
	 * @param userId
	 * @return
	 */
	ArrayList<Journal> findByUserId(Integer userId);
}
