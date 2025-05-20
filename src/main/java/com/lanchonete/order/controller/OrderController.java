package com.lanchonete.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lanchonete.order.model.Order;
import com.lanchonete.order.service.OrderService;
import com.lanchonete.order.service.OrderService.OrderItemRequest;
import com.lanchonete.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins="*", allowedHeaders="*")
public class OrderController {
	
	@Autowired
	private OrderRepository oRepository;
	
	private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
	
	@GetMapping
	public ResponseEntity<List<Order>> getAll(){
		return ResponseEntity.ok(oRepository.findAll());
	}
	
	

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            Order order = orderService.createOrder(request.getClientId(), request.getItems());
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Tratar exceções específicas (ex: cliente não encontrado) com códigos de status apropriados
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Ou um status mais específico
        }
    }

    @PostMapping("/{orderId}/finalize")
    public ResponseEntity<Order> finalizeOrder(@PathVariable Long orderId) {
        try {
            Order finalizedOrder = orderService.finalizeOrder(orderId);
            return ResponseEntity.ok(finalizedOrder);
        } catch (RuntimeException e) {
            // Tratar exceções específicas (ex: pedido não encontrado, status inválido, estoque insuficiente)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Ou um status mais específico
        }
    }
    
 // Classe interna para representar a requisição de criação de pedido
    public static class CreateOrderRequest {
        private Long clientId;
        private List<OrderItemRequest> items;

        public Long getClientId() {
            return clientId;
        }

        public void setClientId(Long clientId) {
            this.clientId = clientId;
        }

        public List<OrderItemRequest> getItems() {
            return items;
        }

        public void setItems(List<OrderItemRequest> items) {
            this.items = items;
        }
    }

}
