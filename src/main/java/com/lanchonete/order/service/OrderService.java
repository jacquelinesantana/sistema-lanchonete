package com.lanchonete.order.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lanchonete.order.model.Order;
import com.lanchonete.repository.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository oRepository;
    //private final ProductService pService;

	// public OrderService(OrderRepository oRepository, ProductService pService) {
    public OrderService(OrderRepository oRepository) {
        this.oRepository = oRepository;
        //this.pService = pService;
    }
	
    @Transactional
    public Order realizarPedido(Order order) {
        // Criar CompletableFutures para as verificações paralelas
        CompletableFuture<Order> discountFuture = applyDiscountIfApplicable(order);
        CompletableFuture<Void> stockFuture = verifyAndUpdateStock(order.getItensPedido());

        // Esperar que ambas as verificações completem
        CompletableFuture.allOf(discountFuture, stockFuture).join(); // Usando join() para esperar o resultado

        // Agora que as verificações passaram, podemos salvar o pedido
        order = oRepository.save(order);
        return order;
    }

    private CompletableFuture<Order> applyDiscountIfApplicable(Order order) {
        return CompletableFuture.supplyAsync(() -> {
            double totalAmount = order.getItensPedido().stream()
                    .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                    .sum();
            if (totalAmount > 150.0) {
                double discount = totalAmount * 0.05;
                order.getTotalValue(totalAmount - discount);
                order.setDescontoAplicado(true); // Assumindo que você tem esse campo
            } else {
            	order.setTotalValue(totalAmount);
            	order.setDescontoAplicado(false);
            }
            return order;
        });
    }

    private CompletableFuture<Void> verifyAndUpdateStock(List<ItemPedido> itensPedido) {
        List<CompletableFuture<Void>> futures = itensPedido.stream()
                .map(item -> CompletableFuture.runAsync(() -> {
                    Produto produto = produtoService.findById(item.getProduto().getId())
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + item.getProduto().getId()));
                    if (produto.getQuantidadeEstoque() >= item.getQuantidade()) {
                        produtoService.atualizarEstoque(produto.getId(), produto.getQuantidadeEstoque() - item.getQuantidade());
                    } else {
                        throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
                    }
                }))
                .collect(Collectors.toList());

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }
	
}
