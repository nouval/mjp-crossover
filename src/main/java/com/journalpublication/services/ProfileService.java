package com.journalpublication.services;

import com.journalpublication.domain.Account;

public interface ProfileService {
	Account register(Account account);
	Account authenticate(String email, String password, String type);
}
