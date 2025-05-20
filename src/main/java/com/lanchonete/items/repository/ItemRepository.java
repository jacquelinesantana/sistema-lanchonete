package com.lanchonete.items.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lanchonete.items.model.OrderItems;

@Repository
public interface ItemRepository extends JpaRepository<OrderItems, Long>{

}
