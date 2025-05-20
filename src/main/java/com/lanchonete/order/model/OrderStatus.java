package com.lanchonete.order.model;

public enum OrderStatus {

	PENDING("Pendente"),
	PROCESSING("Em Preparo"),
	READY("Pronto"),
	DELIVERED("Entregue"),
	CANCELLED("Cancelado");
	
	private String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
