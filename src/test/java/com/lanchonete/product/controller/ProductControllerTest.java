package com.lanchonete.product.controller;

import static org.junit.Assert.assertEquals;

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

import com.lanchonete.product.config.TestConfigProduct;
import com.lanchonete.product.model.Product;
import com.lanchonete.product.repository.ProductRepository;
import com.lanchonete.product.service.ProductService;

@SpringBootTest(webEnvironment  = WebEnvironment.RANDOM_PORT)//ele roda o teste em portas aleatorias para não derrubar api do porta 8080
@TestInstance(TestInstance.Lifecycle.PER_CLASS)//ciclo de vida do teste se da por classe
public class ProductControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired 
	private ProductService productService;
	
	@Autowired
	private ProductRepository productRepository;
	
	private static final String BASE_URL_PRODUCTS = "/products";
	
	@BeforeAll //esse será executado antes dos testes para preparar ambiente dessa classe
	void start() {
		productRepository.deleteAll();
		
		productService.saveP(TestConfigProduct.dataProduct());
	}
	
	@Test
	@DisplayName("try post a new product")//nome do teste facilita ver se deu sucesso 
	public void tryPostNewProduct() {
		Product product = TestConfigProduct.newProduct(null, "Tomates fritos", "Tomates Fritos com carne moida e molho madera",
				44.32, 20L, "Lanches");
		
		HttpEntity<Product> request = new HttpEntity<>(product);
		ResponseEntity<Product> call = testRestTemplate.exchange(BASE_URL_PRODUCTS,
				HttpMethod.POST, request, Product.class);
		
		assertEquals(HttpStatus.CREATED, call.getStatusCode());
				
	}
	
}
