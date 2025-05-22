package com.lanchonete.order.model;

import java.math.BigDecimal;

public class PaymentRequest {

	private BigDecimal value;
    private String description;
    private String email;
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
