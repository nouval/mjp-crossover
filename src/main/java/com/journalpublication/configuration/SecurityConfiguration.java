package com.journalpublication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
			.authorizeRequests()
				.antMatchers("/resources/**", "/webjars/**", "/index", "/register", "/login", "/api/**").permitAll()
				.antMatchers("/console/**").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/index")
				.permitAll();

		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
    }    
}