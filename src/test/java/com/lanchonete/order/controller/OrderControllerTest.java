package com.lanchonete.order.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;



import com.lanchonete.client.model.Client;
import com.lanchonete.client.repository.ClientRepository;

import com.lanchonete.items.repository.ItemRepository;
import com.lanchonete.order.config.TestConfigOrder;
import com.lanchonete.order.model.Order;
import com.lanchonete.order.model.OrderStatus;
import com.lanchonete.order.repository.OrderRepository;
import com.lanchonete.order.service.OrderService;
import com.lanchonete.order.service.OrderService.OrderItemRequest;
import com.lanchonete.product.config.TestConfigProduct;
import com.lanchonete.product.model.Product;
import com.lanchonete.product.repository.ProductRepository;
import com.lanchonete.product.service.ProductService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemRepository itemRepository;

    private static final String BASE_URL_ORDERS = "/orders";
    
    private Client testClient;
    private Product testProduct;

    @BeforeAll
    void start() {
        // Limpar todas as tabelas relacionadas
        itemRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
        clientRepository.deleteAll();

        // Criar e salvar cliente de teste
        testClient = clientRepository.save(TestConfigOrder.dataClient());
        
        // Criar e salvar produto de teste
        testProduct = productRepository.save(TestConfigOrder.dataProduct());
    }

    @Test
    @DisplayName("Deve criar um novo pedido com sucesso")
    public void shouldCreateNewOrder() {
        // Arrange
        List<OrderItemRequest> orderItems = TestConfigOrder.dataOrderItemsRequest(testProduct.getId());
        
        // Act - Usando o service diretamente pois não temos DTO para o Controller
        Order createdOrder = orderService.createOrder(testClient.getId(), orderItems);

        // Assert
        assertNotNull(createdOrder);//criou o order
        assertNotNull(createdOrder.getId());//existe um order com id no banco
        assertEquals(testClient.getId(), createdOrder.getClient().getId()); 
        //conseguimos relacionar um cliente existente ao pedido
        assertEquals(OrderStatus.PENDING, createdOrder.getStatus());
        //existe um pedido com status Pending criado
        assertTrue(createdOrder.getTotalValue() > 0);
        //valor do pedido não ficou zero
        
    }

	
}