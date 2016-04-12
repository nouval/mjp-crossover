package com.journalpublication.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.journalpublication.domain.Journal;

public interface JournalRepository extends CrudRepository<Journal, Integer> {

	// findOne by email & type
	ArrayList<Journal> findByUserId(Integer userId);
}
