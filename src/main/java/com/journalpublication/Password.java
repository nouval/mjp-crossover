package com.journalpublication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.tomcat.util.codec.binary.Base64;

public class Password {

    private String algorithm = "SHA-256";
	private byte[] salt;
	
	public Password() {
		// set default salt
        this.salt = new byte[20];
		SecureRandom random = new SecureRandom();
        random.nextBytes(this.salt);
	}
	
	public Password(String salt) {
		this.setSalt(salt);
	}	

	public void setSalt(String salt) {
		this.salt = this.stringToByte(salt);
	}

	public String getSalt() {
		return Base64.encodeBase64String(this.salt);
	}

	public boolean validatePassword(String hashedPassword, String passwordToValidate) {

		return validatePassword(hashedPassword, this.getSalt(), passwordToValidate);
	}
	
	public boolean validatePassword(String hashedPassword, String salt, String passwordToValidate) {

		return hashedPassword.equals(this.hashPasswordAsBase64(passwordToValidate, this.stringToByte(salt)));
	}

	public String hashPasswordAsBase64(String password) {
		return this.hashPasswordAsBase64(password, null);
	}
	
	public String hashPasswordAsBase64(String password, byte[] salt) {
		try {
	        MessageDigest digest;
			digest = MessageDigest.getInstance(this.algorithm);
	        digest.reset();
	        digest.update(salt == null ? this.salt : salt);

	        return Base64.encodeBase64String(digest.digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
			return null;
		}
	}
	
	private byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);
        } else {
            return Base64.encodeBase64(input.getBytes());
        }
    }	
}
