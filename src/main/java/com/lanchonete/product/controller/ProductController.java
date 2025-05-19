package com.lanchonete.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lanchonete.product.model.Product;
import com.lanchonete.product.repository.ProductRepository;
import com.lanchonete.product.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins="*", allowedHeaders="*")
public class ProductController {

	@Autowired
	private ProductRepository pRepository;
	
	@Autowired 
	private ProductService pService;
	
	@GetMapping
	public ResponseEntity<List<Product>> getAll(){
		return ResponseEntity.ok(pRepository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Product> post(@RequestBody @Valid Product product){
		return pService.saveP(product).map(
				productSave -> ResponseEntity.status(HttpStatus.CREATED).body(productSave))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
}
