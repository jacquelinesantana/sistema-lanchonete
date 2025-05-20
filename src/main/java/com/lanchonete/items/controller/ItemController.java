package com.lanchonete.items.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lanchonete.items.model.OrderItems;
import com.lanchonete.items.repository.ItemRepository;


@RestController
@RequestMapping("/items")
@CrossOrigin(origins="*", allowedHeaders="*")
public class ItemController {

	@Autowired
	private ItemRepository iRepository;
	
	@GetMapping
	public ResponseEntity<List<OrderItems>> getAll(){
		return ResponseEntity.ok(iRepository.findAll());
	}
	
	
}
