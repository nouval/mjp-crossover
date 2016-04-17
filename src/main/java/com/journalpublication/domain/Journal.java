package com.journalpublication.domain;

//import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
//import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Journal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@JsonIgnore
	@Version
	private Integer version;

	private String subject;
	private String tags;
	private String filename;
	
	@JsonIgnore
	@Lob
	private byte[] content;
	
	@JsonIgnore
	private Integer userId;
	
//	@ManyToOne(cascade=CascadeType.ALL)
//	private Account account;

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

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	public String getTags() {
		return this.tags;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public byte[] getContent() {
		return this.content;
	}
}