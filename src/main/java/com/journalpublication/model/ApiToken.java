package com.journalpublication.model;

public class ApiToken {
	private String username;
	private String token;
//	private java.util.Date expiry;
	
	public ApiToken(String username, String token) {
		this.username = username;
		this.token = token;
//		this.expiry = expiry;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return this.token;
	}
	
//	public void setExpiry(java.util.Date expiry) {
//		this.expiry = expiry;
//	}
	
//	public java.util.Date getExpiry() {
//		return this.expiry;
//	}
}
