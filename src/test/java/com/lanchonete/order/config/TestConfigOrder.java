package com.lanchonete.order.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lanchonete.client.config.TestConfig;
import com.lanchonete.client.model.Client;
import com.lanchonete.items.model.OrderItems;
import com.lanchonete.order.model.Order;
import com.lanchonete.order.model.OrderStatus;
import com.lanchonete.order.service.OrderService.OrderItemRequest;
import com.lanchonete.product.config.TestConfigProduct;
import com.lanchonete.product.model.Product;

public class TestConfigOrder {

    // Criar um novo pedido
    public static Order newOrder(Long id,
                                LocalDateTime dateOrder,
                                Double discountValue,
                                Client client,
                                List<OrderItems> orderItems,
                                OrderStatus status,
                                Double totalValue) {
        Order order = new Order();
        order.setId(id);
        order.setDateOrder(dateOrder);
        order.setDiscountValue(discountValue);
        order.setClient(client);
        order.setOrderItems(orderItems);
        order.setStatus(status);
        order.setTotalValue(totalValue);

        return order;
    }

    // Dados padrão para pedido
    public static Order dataOrder(Client client) {
        return newOrder(
            null,
            LocalDateTime.now(),
            0.0,
            client,
            new ArrayList<>(),
            OrderStatus.PENDING,
            0.0
        );
    }

    // Criar um cliente para teste
    public static Client newClient(Long id, String name, String document) {
        Client client = new Client();
        client.setId(id);
        client.setName(name);
        client.setDocument(document);
        return client;
    }

    // Cliente padrão para testes
    public static Client dataClient() {
        return newClient(null, "João Silva", "999999999");
    }

    // Criar um produto para teste
    public static Product newProduct(Long id, String name, String description,
                                   Double price, Long amount, String category) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setAmount(amount);
        product.setCategory(category);
        return product;
    }

    // Produto padrão para testes
    public static Product dataProduct() {
        return newProduct(null, "Hambúrguer", "Hambúrguer com queijo",
                         25.0, 50L, "Lanches");
    }

    // Criar um OrderItemRequest para teste
    public static OrderItemRequest newOrderItemRequest(Long productId, Long quantity) {
        OrderItemRequest request = new OrderItemRequest();
        request.setProductId(productId);
        request.setQuantity(quantity);
        return request;
    }

    // Lista de itens padrão para pedido
    public static List<OrderItemRequest> dataOrderItemsRequest(Long productId) {
        List<OrderItemRequest> items = new ArrayList<>();
        items.add(newOrderItemRequest(productId, 2L));
        return items;
    }
}
