package com.journalpublication.model;

import javax.persistence.Lob;

public class JournalContent {
	private String filename;
	
	@Lob
	private byte[] content;

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getFilename() {
		return this.filename;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public byte[] getContent() {
		return this.content;
	}
}
