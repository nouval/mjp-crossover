package com.journalpublication.model;

public class ApiToken {
	private final String username;
	private final String token;
	private final java.util.Date expiry;
	
	public ApiToken(String username, String token, java.util.Date expiry) {
		this.username = username;
		this.token = token;
		this.expiry = expiry;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public java.util.Date getExpiry() {
		return this.expiry;
	}
}
