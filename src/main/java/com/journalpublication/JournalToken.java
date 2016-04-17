package com.journalpublication;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JournalToken extends AbstractAuthenticationToken {

	/**
	 * Custom Journal Publication token for session authentication
	 */
	private static final long serialVersionUID = 1L;

	/** holds user's email */
	private Object principal;
	/** holds user's Account detail */
	private Object credentials;
	
	/**
	 * Default c'tor
	 * 
	 * @param principal
	 * @param credentials
	 */
	public JournalToken(Object principal, Object credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return this.principal;
	}
}
