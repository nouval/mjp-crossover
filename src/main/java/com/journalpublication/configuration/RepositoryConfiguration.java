package com.journalpublication.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.journalpublication.domain"})
@EnableJpaRepositories(basePackages = {"com.journalpublication.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
	/**
	 * this class instructs spring framework to load entities and repositories class
	 * from given package at com.journalpublication.domain & com.journalpublication.repositories
	 */
}