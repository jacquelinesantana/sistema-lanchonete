package com.lanchonete.order.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanchonete.client.model.Client;
import com.lanchonete.client.repository.ClientRepository;
import com.lanchonete.items.model.OrderItems;
import com.lanchonete.items.repository.ItemRepository;
import com.lanchonete.order.model.Order;
import com.lanchonete.order.model.OrderStatus;
import com.lanchonete.product.model.Product;
import com.lanchonete.product.service.ProductService;
import com.lanchonete.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository orderItemRepository;
    private final ClientRepository clientRepository;
    private final ProductService productService;

    public OrderService(
    		OrderRepository orderRepository, 
    		ItemRepository orderItemRepository, 
    		ClientRepository clientRepository, 
    		ProductService productService) {
    	
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.clientRepository = clientRepository;
        this.productService = productService;
    }

    @Transactional
    public Order createOrder(Long clientId, List<OrderItemRequest> orderItemsRequest) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clientId));

        Order order = new Order();
        order.setClient(client);
        order.setDateOrder(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setDiscountValue(0.0); // Inicialmente sem desconto
        order.setTotalValue(0.0);   // Inicialmente zero

        order = orderRepository.save(order);

        List<OrderItems> items = new ArrayList<>();
        double totalAmount = 0.0;

        for (OrderItemRequest itemRequest : orderItemsRequest) {
            Product product = productService.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + itemRequest.getProductId()));

            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setItemValeu(product.getPrice()); // Preço no momento do pedido
            orderItem = orderItemRepository.save(orderItem);
            items.add(orderItem);
            totalAmount += orderItem.getQuantity() * orderItem.getItemValeu();
        }

        order.setOrderItems(items);
        order.setTotalValue(totalAmount);

        // Aplica o desconto após calcular o total
        if (totalAmount > 150.0) {
            order.setDiscountValue(totalAmount * 0.05);
            order.setTotalValue(totalAmount - order.getDiscountValue());
        }

        return orderRepository.save(order);
    }

    // Classe interna para representar os itens do pedido na requisição
    public static class OrderItemRequest {
        private Long productId;
        private Long quantity;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Long getQuantity() {
            return quantity;
        }

        public void setQuantity(Long quantity) {
            this.quantity = quantity;
        }
    }

    // Método para finalizar o pedido e atualizar o estoque
    @Transactional
    public Order finalizeOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + orderId));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Pedido não pode ser finalizado pois seu status é: " + order.getStatus());
        }

        for (OrderItems item : order.getOrderItems()) {
            Product product = productService.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + item.getProduct().getId()));
            if (product.getAmount() < item.getQuantity()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + product.getName());
            }
            productService.atualizarEstoque(product.getId(), product.getAmount() - item.getQuantity());
        }

        order.setStatus(OrderStatus.PROCESSING); // Ou outro status apropriado
        return orderRepository.save(order);
    }

    // Outros métodos para listar, cancelar, etc.
}