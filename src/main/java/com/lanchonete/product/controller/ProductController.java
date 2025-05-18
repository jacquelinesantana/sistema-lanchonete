package com.lanchonete.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lanchonete.product.model.Product;
import com.lanchonete.product.repository.ProductRepository;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins="*", allowedHeaders="*")
public class ProductController {

	@Autowired
	private ProductRepository pRepository;
	
	public ResponseEntity<List<Product>> getAll(){
		return ResponseEntity.ok(pRepository.findAll());
	}
}
