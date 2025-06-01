package com.lanchonete.product.config;

import com.lanchonete.product.model.Product;

public class TestConfigProduct {
	
	//create a new product
	public static Product newProduct(Long id, 
			String name, 
			String description, 
			Double price, 
			Long amount,
			String category) {
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setDescription(description);
		product.setPrice(price);
		product.setAmount(amount);
		product.setCategory(category);
		
		return product;
		
	}
	
	public static Product dataProduct() {
		return newProduct(null, "Fritas", "Fritas com molho da casa",
				15.32, 20L, "Lanches");
	}

}
