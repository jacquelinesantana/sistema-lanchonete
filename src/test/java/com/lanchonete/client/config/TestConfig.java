package com.lanchonete.client.config;

import com.lanchonete.client.model.Client;

public class TestConfig {

	//create a new client method
	public static Client newClient(Long i, String name, String document) {
		Client client = new Client();
		client.setId(i);
		client.setName(name);
		client.setDocument(document);
		return client;
	}
	
	public static Client dataClient() {
		return newClient(null, "Rita", "1234567890");
	}
	
}