package com.lanchonete.order.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lanchonete.client.config.TestConfig;
import com.lanchonete.client.model.Client;
import com.lanchonete.items.model.OrderItems;
import com.lanchonete.order.model.Order;
import com.lanchonete.order.model.OrderStatus;
import com.lanchonete.product.config.TestConfigProduct;
import com.lanchonete.product.model.Product;

public class TestConfigOrder extends TestConfigProduct {

	public static Order newOrder(Long id,
			 LocalDateTime dateOrder,
			 Double discountValue,
             Client client, OrderStatus status, 
             Double totalValue) {
		 
		Order order = new Order();
		order.setId(id);
		order.setDateOrder(dateOrder);
		order.setDiscountValue(discountValue);
		order.setClient(client);
		order.setStatus(status);
		order.setTotalValue(totalValue);
		
		return order;
	}
	 
	 public static OrderItems newOrderItem(Long id,
			 Product product, 
			 Long quantity, 
			 Double itemValue, 
			 Order order) {
	        OrderItems item = new OrderItems();
	        item.setId(id);
	        item.setProduct(product);
	        item.setQuantity(quantity);
	        item.setItemValeu(itemValue);
	        item.setOrder(order);
	        return item;
	 }
	 public static Order dataOrder() {
	        // Cria um cliente de exemplo usando a TestConfig do cliente
	        Client client = TestConfig.newClient(null, "João Teste", "11122233344");

	        Product product = TestConfigProduct.newProduct(null, "Tomates fritos",
	        		"Tomates Fritos com carne moida e molho madera",
				44.32, 20L, "Lanches");
	        // Cria o objeto Order
	        Order order = newOrder(
	            null, // ID será gerado pelo banco de dados
	            LocalDateTime.now(),
	            5.0, // Exemplo de desconto
	            client,
	            OrderStatus.PENDING,
	            38.0 // Exemplo de valor total (2*15 + 1*8 - 5 = 30 + 8 - 5 = 33) - Ajuste conforme sua lógica de cálculo
	            
	        );
	        // Cria uma lista de itens de pedido
	        List<OrderItems> items = new ArrayList<>();
	        // O Order é passado como null inicialmente e será setado após a criação do Order
	        items.add(newOrderItem(null, product, 2L, 88.64, order));
	        

	        

	        // Associa cada item de pedido ao Order recém-criado
	        for (OrderItems item : items) {
	            item.setOrder(order);
	        }

	        return order;
	    }
	
}
