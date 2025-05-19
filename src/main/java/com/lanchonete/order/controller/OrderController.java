package com.lanchonete.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lanchonete.order.model.Order;
import com.lanchonete.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins="*", allowedHeaders="*")
public class OrderController {
	
	@Autowired
	private OrderRepository oRepository;
	
	@GetMapping
	public ResponseEntity<List<Order>> getAll(){
		return ResponseEntity.ok(oRepository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Order> post(@RequestBody Order order){
		return ResponseEntity.ok(oRepository.save(order));
	}

}
