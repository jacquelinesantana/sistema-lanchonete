package com.lanchonete.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lanchonete.client.model.Client;
import com.lanchonete.client.repository.ClientRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins="*", allowedHeaders="*")
public class ClientController {

	@Autowired
	private ClientRepository cRepository;
	
	@GetMapping
	public ResponseEntity<List<Client>> getAll(){
		return ResponseEntity.ok(cRepository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Client> post(@Valid @RequestBody Client client){
		return ResponseEntity.ok(cRepository.save(client));
	}
	
	
}
