package com.lanchonete.client.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lanchonete.client.model.Client;
import com.lanchonete.client.model.dto.ClientDTO;
import com.lanchonete.client.repository.ClientRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins="*", allowedHeaders="*")
public class ClientController {

	@Autowired
	private ClientRepository cRepository;
	
	@GetMapping
    public ResponseEntity<List<ClientDTO>> getAll(){
        List<Client> clients = cRepository.findAll(); // Usar o Service
        List<ClientDTO> clientDTOs = clients.stream()
            .map(client -> new ClientDTO( client.getName(),  client.getDocument() ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(clientDTOs);
    }
	
	@PostMapping
    public ResponseEntity<ClientDTO> post(@Valid @RequestBody ClientDTO clientDTO){
        try {
            Client clientToSave = new Client();
            clientToSave.setName(clientDTO.getName());
            clientToSave.setDocument(clientDTO.getDocument());

            Client savedClient = cRepository.save(clientToSave);

            ClientDTO responseDTO = new ClientDTO(
                savedClient.getName(),
                savedClient.getDocument()
            );

            
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        } catch (Exception e) {
            
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage()); // Loga o erro para depuração
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
            
    }
	
	@GetMapping("/{id}")
	public  ResponseEntity<ClientDTO> getById(@PathVariable Long id){
		try{
			ClientDTO clientToRetorn = new ClientDTO();
			clientToRetorn.setName(cRepository.findById(id).get().getName());
			clientToRetorn.setDocument(cRepository.findById(id).get().getDocument());
			return new ResponseEntity<>(clientToRetorn, HttpStatus.OK);
		}catch  (Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Client> client = cRepository.findById(id);
		if(client.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		cRepository.deleteById(id);
	}
	
	@PutMapping
	public ResponseEntity<Client> put (@Valid @RequestBody Client client){
		if(cRepository.existsById(client.getId())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(cRepository.save(client));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
}
