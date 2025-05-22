package com.lanchonete.product.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class ProductDTO {

	@NotBlank(message="The atribute Name can't to be empty")
	private String name;
	
	private String description;
	
	private Double price;
	
	private Long amount;
	
	@NotEmpty(message="The atribute category can't to be empty")
	private String category;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ProductDTO(String name, String description,
			Double price, Long amount, String category) {
		
		this.name = name;
		this.description = description;
		this.price = price;
		this.amount = amount;
		this.category = category;
	}
	public ProductDTO() {}
	
	
}
