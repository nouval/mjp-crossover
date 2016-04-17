package com.journalpublication.domain;


//import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"email", "type"})})
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Version
	private Integer version;
	
	private String fullname;
	private String email;
	private String password;
	private String type;
	private String salt;

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Integer getVersion() {
		return this.version;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getFullname() {
		return this.fullname;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	/**
	 * Sets user's salt to be used to hashed the password
	 * @param salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	/**
	 * Returns user's salt to verify hashed password
	 * @return
	 */
	public String getSalt() {
		return this.salt;
	}	
}
