package com.lanchonete.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lanchonete.product.model.Product;
import com.lanchonete.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ProductService {


	    private final ProductRepository pRepository;
		

	    @Autowired
	    public ProductService(ProductRepository pRepository) {
	        this.pRepository = pRepository;
	    }

	    public List<Product> listAll() {
	        return pRepository.findAll();
	    }

	    public Optional<Product> findById(Long id) {
	        return pRepository.findById(id);
	    }

	    @Transactional
	    public Optional<Product> saveP(Product product) {
	        // Aqui você pode adicionar validações ou lógica de negócios antes de salvar
	        return Optional.of(pRepository.save(product));
	    }

		/*
		 * @Transactional public Product atualizar(Long id, Product pUpdate) { return
		 * pRepository.findById(id) .map(product -> {
		 * 
		 * product.setName(pUpdate.getName()); product.setPrice(pUpdate.getPrice());
		 * product.setAmount(pUpdate.getAmount());
		 * product.setDescription(pUpdate.getDescription()); // Outros atributos a serem
		 * atualizados return pUpdate.save(product); }) .orElse(null); // Ou lançar uma
		 * exceção indicando que o produto não foi encontrado }
		 */
	    @Transactional
	    public void deletar(Long id) {
	    	pRepository.deleteById(id);
	    }

	    @Transactional
	    public void atualizarEstoque(Long id, Long newAmount) {
	    	pRepository.findById(id)
	                .ifPresent(product -> {
	                	product.setAmount(newAmount);
	                    pRepository.save(product);
	                });
	        // Se o produto não for encontrado, a operação simplesmente não faz nada.
	        // Você pode optar por lançar uma exceção aqui, dependendo da sua necessidade.
	    }

	    @Transactional
	    public void decreaseStock(Long id, int quantity) {
	    	pRepository.findById(id)
	                .ifPresent(product -> {
	                    if (product.getAmount() >= quantity) {
	                    	product.setAmount(product.getAmount() - quantity);
	                        pRepository.save(product);
	                    } else {
	                        throw new RuntimeException("Estoque insuficiente para o produto: " + product.getName());
	                    }
	                });
	    }
}
