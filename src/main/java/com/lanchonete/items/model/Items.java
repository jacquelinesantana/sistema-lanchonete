package com.lanchonete.items.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lanchonete.order.model.Order;
import com.lanchonete.product.model.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_items")
public class Items {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JsonIgnoreProperties("items")
	private Order order;
	
	@ManyToOne
	@JsonIgnoreProperties("items")
	private Product product;
	
	private Long amount;
	
	private Double itemValeu;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Double getItemValeu() {
		return itemValeu;
	}

	public void setItemValeu(Double itemValeu) {
		this.itemValeu = itemValeu;
	}
	
}
