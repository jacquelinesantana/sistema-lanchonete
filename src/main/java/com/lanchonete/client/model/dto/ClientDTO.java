package com.lanchonete.client.model.dto;

public class ClientDTO {

	private String name;
	private String document;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	
	public ClientDTO() {}
	public ClientDTO(String name, String document) {
		super();
		this.name = name;
		this.document = document;
	}
	
	
	
}
