package com.lanchonete.client.controller;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lanchonete.client.config.TestConfig;
import com.lanchonete.client.model.Client;
import com.lanchonete.client.repository.ClientRepository;

@SpringBootTest(webEnvironment  = WebEnvironment.RANDOM_PORT)//ele roda o teste em portas aleatorias para não derrubar api do porta 8080
@TestInstance(TestInstance.Lifecycle.PER_CLASS)//ciclo de vida do teste se da por classe
public class ClientContollerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private ClientRepository clientRepository;
	private static final String BASE_URL_CLIENTS = "/clients";
	
	@BeforeAll //esse será executado antes dos testes para preparar ambiente dessa classe
	void start() {
		clientRepository.deleteAll();//apagar os registros do banco de teste
		clientRepository.save(TestConfig.dataClient());//cria o primeiro cliente
	}
	
	@Test
	@DisplayName("Try post a new client")//nome do teste
	public void tryPostANewClient() {
		Client client = TestConfig.newClient(null, "Pedro", "1234567891");
		
		HttpEntity<Client> request = new HttpEntity<>(client);//preparar o request
		ResponseEntity<Client> call =  testRestTemplate.exchange(
				BASE_URL_CLIENTS , HttpMethod.POST, request, Client.class);
		
		assertEquals(HttpStatus.CREATED, call.getStatusCode());
		
	}
	
	@Test
	@DisplayName("Try update a client data")
	public void tryUpdate() {
		
		
		Client registeredClient = TestConfig.newClient(null, "Renata", "1234567892");
		clientRepository.save(registeredClient);
		
		Client updateDataClient = TestConfig.newClient(registeredClient.getId(),"Renata Santos", "1234567892");
		
		HttpEntity<Client> request =  new HttpEntity<>(updateDataClient);
		
		ResponseEntity<Client> call = testRestTemplate.exchange(BASE_URL_CLIENTS, HttpMethod.PUT, request, Client.class);
		
		assertEquals(HttpStatus.OK, call.getStatusCode());//valida se o retorno foi um ok
		assertEquals("Renata Santos", call.getBody().getName());//validar se o nome foi alterado

	}
}
